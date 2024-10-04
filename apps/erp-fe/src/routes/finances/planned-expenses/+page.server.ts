import { MoneyOperationType } from '$lib/finances/types/financialTypes'
import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { Actions } from './$types'
import { FINANCES_URL } from '$lib/scripts/urls'
import { redirect } from '@sveltejs/kit'

export const actions = {
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
