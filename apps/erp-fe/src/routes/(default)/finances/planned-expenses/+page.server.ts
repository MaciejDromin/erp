import { MoneyOperationType } from '$lib/finances/types/financialTypes'
import { securedExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { Actions } from './$types'
import { GATEWAY_URL } from '$lib/scripts/urls'
import { FINANCES } from '$lib/scripts/serviceKey.ts'
import { redirect } from '@sveltejs/kit'

export const actions = {
  abandon: async ({ request, cookies }) => {
    const data = await request.formData()
    const expensesArray = JSON.parse(data.get('plannedExpensesArr'))
    for (const val of expensesArray) {
      const ret = await securedExternalApiRequest(
        `${GATEWAY_URL}/${FINANCES}/planned-expenses/${val.id}/abandon`,
        HttpMethods.PATCH,
        cookies.get('Authorization'),
        cookies
      )
      if (ret.status === 204 && ret.headers.get('redirected') === 'true')
        throw redirect(303, ret.headers.get('location'))
    }
  },
  complete: async ({ request }) => {
    const data = await request.formData()
    const expensesMap = JSON.parse(data.get('plannedExpensesMap'))
    for (const [key, val] of Object.entries(expensesMap)) {
      const body = {
        actualAmount: val,
      }
      const ret = await securedExternalApiRequest(
        `${GATEWAY_URL}/${FINANCES}/planned-expenses/${key}/complete`,
        HttpMethods.PATCH,
        cookies.get('Authorization'),
        cookies,
        body
      )
      if (ret.status === 204 && ret.headers.get('redirected') === 'true')
        throw redirect(303, ret.headers.get('location'))
    }
  },
} satisfies Actions
