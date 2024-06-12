from abc import ABC, abstractmethod

class ScanningStrategy(ABC):

    @abstractmethod
    def parse_data(self, to_parse: []) -> []:
        pass


    @abstractmethod
    def is_applicable(self, file_extension: str) -> bool:
        pass
