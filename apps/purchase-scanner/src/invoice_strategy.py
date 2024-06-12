import scanning_strategy
import pdftotext
import tabula
import pandas
import regex as re


class InvoiceStrategy(scanning_strategy.ScanningStrategy):


    EXTENSION = "pdf"

    DATE_PATTERN = r'\d{4}-\d{2}-\d{2}'
    ADDRESS_PATTERN = r'Sprzedawca\n.+'


    def scan_invoices(self, invoices):
        ret = []
        for invoice in invoices:
            parsed_invoice = {}
            with open(invoice, 'rb') as f:
                pdf = pdftotext.PDF(f)

                parsed_invoice["data"] = '\n\n'.join(pdf)
                parsed_invoice["source"] = invoice[6:]


            tables = tabula.read_pdf(invoice, stream=True, pages="all")

            if tables:
                items_found = False
                valid_dataframes = []
                for df in tables:
                    if "Nr" in df.columns:
                        valid_dataframes.append(df)
                        items_found = True
                        
                if not items_found:
                    # TODO: could not find items in table, send error on rabbit
                    continue

                merged = pandas.concat(valid_dataframes)

                merged = merged[merged['Nr'].notna()]

                parsed_invoice["items"] = merged
            else:
                # TODO: could not find tables in invoice, send error on rabbit
                continue

            ret.append(parsed_invoice)


        return ret
    

    def parse_items(self, items):
        ret = []
        for ri, row in items.iterrows():
            receipt_item = {
                "name": row["Nazwa produktu"],
                "unit": row["J.m."].upper(),
                "unit_price": float(row["C. jedn."].replace(',','.')),
                "price": float(row["Wartość"].replace(',','.')),
                "quantity": float(row["Ilość"].replace(',','.'))
            }
            ret.append(receipt_item)

        return ret


    def parse_invoices(self, scanned_invoices):
        ret = []

        for invoice in scanned_invoices:
            date = re.search(self.DATE_PATTERN, invoice["data"])
            date = date.group().strip()
            address = re.search(self.ADDRESS_PATTERN, invoice["data"])
            address = address.group().strip().split("\n")[1]
            processed_invoice = {
                "address": address,
                "items": self.parse_items(invoice["items"]),
                "date": date,
                "source": invoice["source"]
            }
            ret.append(processed_invoice)

        return ret



    def parse_data(self, to_parse: []) -> []:
        scanned_invoices = self.scan_invoices(to_parse)
        return self.parse_invoices(scanned_invoices)


    def is_applicable(self, file_extension: str) -> bool:
        return self.EXTENSION == file_extension
