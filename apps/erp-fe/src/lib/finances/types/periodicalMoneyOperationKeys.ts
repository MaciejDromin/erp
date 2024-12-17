const periodicalMoneyOperationKeys = {
  operationId: {
    data: ['operation', 'id'],
    form: ['id', 'val'],
  },
  category: {
    data: ['operation', 'operationCategory'],
    form: ['category', 'val'],
  },
  amount: {
    data: ['operation', 'amount', 'value'],
    form: ['amount', 'value', 'val'],
  },
  currencyCode: {
    data: ['operation', 'amount', 'currencyCode'],
    form: ['amount', 'currencyCode', 'val'],
  },
  operationDescription: {
    data: ['operation', 'operationDescription'],
    form: ['operationDescription', 'val'],
  },
  operationType: {
    data: ['operation', 'operationType'],
    form: ['operationType', 'val'],
  },
  repetitionPeriod: {
    data: ['operation', 'repetitionPeriod'],
    form: ['repetitionPeriod', 'val'],
  },
  nextApplicableMonth: {
    data: ['operation', 'nextApplicableMonth'],
    form: ['nextApplicableMonth', 'val'],
  },
}

export default periodicalMoneyOperationKeys 
