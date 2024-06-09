import scanning_strategy


class InvoiceStrategy(scanning_strategy.ScanningStrategy):

    EXTENSION = "pdf"
     
    def parse_data(self, to_parse: []) -> []:
        pass


    def is_applicable(self, file_extension: str) -> bool:
        return self.EXTENSION == file_extension
