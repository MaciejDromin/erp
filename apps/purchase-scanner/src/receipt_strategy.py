from imutils.perspective import four_point_transform
import scanning_strategy
import pytesseract
import regex as re
import argparse
import imutils
import cv2
import json
import math


class ReceiptStrategy(scanning_strategy.ScanningStrategy):


    ITEM_PATTERN = r'(?<=~)(.*?)(?=~)'
    DATE_PATTERN = r'\d{4}-\d{2}-\d{2}'
    PRICE_PATTERN = r'\d+[,.]\d{2}\s{0,1}[A-Z0-9]$'
    UNIT_PRICE_PATTERN = r'\s{0,1}[x*+«]\s{0,1}\d+,\d{2}'
    QUANTITY_UNIT_PATTERN = r'[\s|~]\d+,{0,1}\d{0,4}(SZT|KG|L){0,1}$'
    UNIT_PATTERN = r'(SZT|KG|L)'
    EXTENSION = "jpg"
    

    def get_contours_from_receipt(self, receipt):
        orig = cv2.imread(receipt)
        image = orig.copy()
        image = imutils.resize(image, width=500)
        ratio = orig.shape[1] / float(image.shape[1])

        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
        blurred = cv2.GaussianBlur(gray, (5, 5,), 0)
        edged = cv2.Canny(blurred, 75, 200)

        cnts = cv2.findContours(edged.copy(), cv2.RETR_EXTERNAL,
            cv2.CHAIN_APPROX_SIMPLE)
        cnts = imutils.grab_contours(cnts)
        return orig, ratio, sorted(cnts, key=cv2.contourArea, reverse=True)
        


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

            receiptTransformed = four_point_transform(orig, receiptCnt.reshape(4, 2) * ratio)

            text = pytesseract.image_to_string(
                cv2.cvtColor(receiptTransformed, cv2.COLOR_BGR2RGB), config="--psm 4", lang="pol")

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
        result = re.sub(r'[a-zA-Z*«+]', '', value)
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
            if date != None:
                date = date.group()
            else:
                splitted_date = source[:source.find("/")].split("-")
                date = "{}-{}-{}".format(splitted_date[0], splitted_date[1], "01")

            address=preprocessed.strip()[:45]
            items = re.findall(self.ITEM_PATTERN, preprocessed)
            processed_receipt = {
                "address": re.sub(r'~', ';', address),
                "items": self.parse_items(items),
                "date": date,
                "source": source
            }
            ret.append(processed_receipt)
    
        return ret


    def parse_data(self, to_parse: []) -> []:
        scanned_receipts = self.scan_receipts(to_parse)
        return self.parse_receipts(scanned_receipts)


    def is_applicable(self, file_extension: str) -> bool:
        return self.EXTENSION == file_extension
