import { MoneyOperationType } from '$lib/finances/types/financialTypes'
import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { Actions } from './$types'
import { FINANCES_URL } from '$lib/scripts/urls'
import { redirect } from '@sveltejs/kit'

export const actions = {
  abandon: async ({ request }) => {
    const data = await request.formData()
    const expensesArray = JSON.parse(data.get('plannedExpensesArr'))
    for (const val of expensesArray) {
      await unsecuredExternalApiRequest(
        FINANCES_URL + `/finances/planned-expenses/${val.id}/abandon`,
        HttpMethods.PATCH
      )
    }
  },
  complete: async ({ request }) => {
    const data = await request.formData()
    const expensesMap = JSON.parse(data.get('plannedExpensesMap'))
    for (const [key, val] of Object.entries(expensesMap)) {
      const body = {
        actualAmount: val,
      }
      await unsecuredExternalApiRequest(
        FINANCES_URL + `/finances/planned-expenses/${key}/complete`,
        HttpMethods.PATCH,
        body
      )
    }
  },
} satisfies Actions
