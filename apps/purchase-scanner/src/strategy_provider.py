import invoice_strategy
import receipt_strategy


class StrategyProvider():

    strategies = [receipt_strategy.ReceiptStrategy(), invoice_strategy.InvoiceStrategy()]

    def get_strategy_for_extension(self, file_extension):
        return next(x for x in self.strategies if x.is_applicable(file_extension))
