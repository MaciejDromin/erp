import { MoneyOperationType } from '$lib/finances/types/financialTypes'
import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { Actions } from './$types'
import { FINANCES_URL } from '$lib/scripts/urls'
import { redirect } from '@sveltejs/kit'

export const actions = {
  default: async ({ request }) => {
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
    throw redirect(303, '/finances/planned-expenses')
  },
} satisfies Actions
