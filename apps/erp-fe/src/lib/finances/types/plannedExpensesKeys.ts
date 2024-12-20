const plannedExpensesKeys = {
  id: {
    data: ['plannedExpense', 'id'],
    form: ['id', 'val'],
  },
  category: {
    data: ['plannedExpense', 'operationCategory'],
    form: ['category', 'val'],
  },
  amount: {
    data: ['plannedExpense', 'plannedAmount', 'value'],
    form: ['plannedAmount', 'value', 'val'],
  },
  currencyCode: {
    data: ['plannedExpense', 'plannedAmount', 'currencyCode'],
    form: ['plannedAmount', 'currencyCode', 'val'],
  },
  operationDescription: {
    data: ['plannedExpense', 'operationDescription'],
    form: ['operationDescription', 'val'],
  },
  operationType: {
    data: ['plannedExpense', 'operationType'],
    form: ['operationType', 'val'],
  },
  plannedYear: {
    data: ['plannedExpense', 'plannedYear'],
    form: ['plannedYear', 'val'],
  },
  plannedMonth: {
    data: ['plannedExpense', 'plannedMonth'],
    form: ['plannedMonth', 'val'],
  },
}

export default plannedExpensesKeys
