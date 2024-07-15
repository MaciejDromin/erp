import { MoneyOperationType } from '$lib/finances/types/financialTypes'
import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { Actions } from './$types'
import { FINANCES_URL } from '$lib/scripts/urls'

export const actions = {
  create: async ({ request }) => {
    const data = await request.formData()
    const body = {
      plannedAmount: {
        value: data.get('plannedAmount'),
        currencyCode: data.get('currencyCode'),
      },
      operationDescription: data.get('operationDescription'),
      plannedYear: data.get('plannedYear'),
      plannedMonth: data.get('plannedMonth'),
      operationCategoryId: data.get('categoryId'),
      operationType: MoneyOperationType.EXPENSES,
    }
    await unsecuredExternalApiRequest(
      FINANCES_URL + '/finances/planned-expenses',
      HttpMethods.POST,
      body
    )
  },
  abandon: async ({ request }) => {
    const data = await request.formData()
    await unsecuredExternalApiRequest(
      FINANCES_URL +
        `/finances/planned-expenses/${data.get('plannedExpensesId')}/abandon`,
      HttpMethods.PATCH
    )
  },
  complete: async ({ request }) => {
    const data = await request.formData()
    const body = {
      actualAmount: {
        value: data.get('actualAmount'),
        currencyCode: data.get('currency'),
      },
    }
    await unsecuredExternalApiRequest(
      FINANCES_URL +
        `/finances/planned-expenses/${data.get('plannedExpensesId')}/complete`,
      HttpMethods.PATCH,
      body
    )
  },
} satisfies Actions
