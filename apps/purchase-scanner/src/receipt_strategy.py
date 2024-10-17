from datetime import datetime
import regex as re
import numpy as np
import scanning_strategy
import pytesseract
import cv2
import math


class ReceiptStrategy(scanning_strategy.ScanningStrategy):


    ITEM_PATTERN = r'(?<=~)(.*?)(?=~)'
    DATE_PATTERN = r'\d{4}-\d{2}-\d{2}'
    PRICE_PATTERN = r'\d+[,.]\d{2}\s{0,1}[A-Z0-9]$'
    UNIT_PRICE_PATTERN = r'\s{0,1}[x*+«]\s{0,1}\d+,\d{2}'
    QUANTITY_UNIT_PATTERN = r'[\s|~]\d+,{0,1}\d{0,4}(SZT|KG|L){0,1}$'
    UNIT_PATTERN = r'(SZT|KG|L)'
    EXTENSION = "jpg"


    def opencv_resize(self, image, ratio):
        width = int(image.shape[1] * ratio)
        height = int(image.shape[0] * ratio)
        dim = (width, height)
        return cv2.resize(image, dim, interpolation = cv2.INTER_AREA)


    def get_contours_from_receipt(self, receipt):
        image = cv2.imread(receipt)
        orig = image.copy()
        ratio = 500 / image.shape[0]

        image = self.opencv_resize(image, ratio)
        
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
        blurred = cv2.GaussianBlur(gray, (5, 5), 0)
        rectKernel = cv2.getStructuringElement(cv2.MORPH_RECT, (9, 9))
        dilated = cv2.dilate(blurred, rectKernel)

        edged = cv2.Canny(dilated, 100, 200, apertureSize=3)
        cnts, hierarchy = cv2.findContours(edged, cv2.RETR_TREE, cv2.CHAIN_APPROX_SIMPLE)
        sorted_cnts = sorted(cnts, key=cv2.contourArea, reverse=True)[:5]

        return orig, ratio, sorted_cnts


    def contour_to_rect(self, contour, ratio):
        pts = contour.reshape(4, 2)
        rect = np.zeros((4, 2), dtype = "float32")
        s = pts.sum(axis = 1)
        rect[0] = pts[np.argmin(s)]
        rect[2] = pts[np.argmax(s)]
        diff = np.diff(pts, axis = 1)
        rect[1] = pts[np.argmin(diff)]
        rect[3] = pts[np.argmax(diff)]
        return rect / ratio


    def wrap_perspective(self, img, rect):
        (tl, tr, br, bl) = rect
        widthA = np.sqrt(((br[0] - bl[0]) ** 2) + ((br[1] - bl[1]) ** 2))
        widthB = np.sqrt(((tr[0] - tl[0]) ** 2) + ((tr[1] - tl[1]) ** 2))
        heightA = np.sqrt(((tr[0] - br[0]) ** 2) + ((tr[1] - br[1]) ** 2))
        heightB = np.sqrt(((tl[0] - bl[0]) ** 2) + ((tl[1] - bl[1]) ** 2))
        maxWidth = max(int(widthA), int(widthB))
        maxHeight = max(int(heightA), int(heightB))
        dst = np.array([
            [0, 0],
            [maxWidth - 1, 0],
            [maxWidth - 1, maxHeight - 1],
            [0, maxHeight - 1]], dtype = "float32")
        M = cv2.getPerspectiveTransform(rect, dst)
        return cv2.warpPerspective(img, M, (maxWidth, maxHeight))


    def scan_receipts(self, receipts):
        ret = []
        for receipt in receipts:
            orig, ratio, cnts = self.get_contours_from_receipt(receipt)

            receiptCnt = None

            for c in cnts:
                peri = cv2.arcLength(c, True)
                approx = cv2.approxPolyDP(c, 0.032 * peri, True)

                if len(approx) == 4:
                    receiptCnt = approx
                    break
            if receiptCnt is None:
                print("No outline found for receipt: " + receipt + " skipping...")
                # TODO: send kafka message
                continue

            scanned = self.wrap_perspective(orig.copy(), self.contour_to_rect(receiptCnt, ratio))

            text = pytesseract.image_to_string(
                cv2.cvtColor(scanned, cv2.COLOR_BGR2GRAY), config="--psm 6", lang="pol")

            parsed_receipt = {
                "data": text,
                "source": receipt[6:]
            }

            ret.append(parsed_receipt)

        return ret


    def find_and_extract(self, item, pattern, default):
        value = re.search(pattern, item)
        if value != None:
            value = value.group().strip()
        else:
            value = default

        string_ext = re.sub(pattern, '', item).strip()
        return string_ext, value


    def convert_to_double(self, value):
        if isinstance(value, float):
            return value
        result = re.sub(r'[a-zA-Z*«+\s]', '', value)
        result = re.sub(r',', '.', result)
        return float(result)


    def remove_separator(self, string):
        return re.sub(r'~', '', string)


    def parse_items(self, items):
        ret = []
        for item in items:
            ext, price = self.find_and_extract(item, self.PRICE_PATTERN, 1.0)
            ext, unit_price = self.find_and_extract(ext, self.UNIT_PRICE_PATTERN, 1.0)
            ext, quantity_unit = self.find_and_extract(ext, self.QUANTITY_UNIT_PATTERN, "1SZT")
            quantity, unit = self.find_and_extract(quantity_unit, self.UNIT_PATTERN, "SZT")
            trimmed_name = self.remove_separator(ext.strip())

            converted_price = self.convert_to_double(price)
            converted_unit_price = self.convert_to_double(unit_price)

            if (math.isclose(converted_price, 1.0, rel_tol=1e-03, abs_tol=0.0) and 
                math.isclose(converted_unit_price, 1.0, rel_tol=1e-03, abs_tol=0.0)):
                    print("Probably not an item, skipping")
                    continue

            receipt_item = {
                "name": trimmed_name,
                "unit": unit,
                "unit_price": converted_unit_price,
                "price": converted_price,
                "quantity": self.convert_to_double(self.remove_separator(quantity))
            }
            ret.append(receipt_item)

        return ret


    def parse_receipts(self, scannedReceipts):
        ret = []

        for receipt in scannedReceipts:
            preprocessed = receipt["data"].replace("\n", "~")
            date = re.search(self.DATE_PATTERN, preprocessed)
            source = receipt["source"]
            splitted_date = source[:source.find("/")].split("-")
            file_date = "{}-{}-{}".format(splitted_date[0], splitted_date[1], "01")
            if date != None:
                date = date.group()
            else:
                date = file_date

            file_date = datetime.strptime(file_date, '%Y-%m-%d')

            try:
                date = datetime.strptime(date, '%Y-%m-%d')
            except:
                date = file_date

            if date.month != file_date.month:
                date = file_date

            address=preprocessed.strip()[:45]
            items = re.findall(self.ITEM_PATTERN, preprocessed)
            processed_receipt = {
                "address": re.sub(r'~', ';', address),
                "items": self.parse_items(items),
                "date": date.strftime('%Y-%m-%d'),
                "source": source
            }
            ret.append(processed_receipt)
    
        return ret


    def parse_data(self, to_parse: []) -> []:
        scanned_receipts = self.scan_receipts(to_parse)
        return self.parse_receipts(scanned_receipts)


    def is_applicable(self, file_extension: str) -> bool:
        return self.EXTENSION == file_extension
