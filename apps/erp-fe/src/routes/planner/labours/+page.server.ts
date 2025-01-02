import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { Actions } from './$types'
import { GATEWAY_URL } from '$lib/scripts/urls'
import { PLANNER } from '$lib/scripts/serviceKey.ts'

export const actions = {
  default: async ({ cookies, request }) => {
    const data = await request.formData()
    const body = {
      name: data.get('name'),
      unit: data.get('unit'),
      rateAmount: {
        value: data.get('value'),
        currencyCode: data.get('currency'),
      },
      contractorName: data.get('contractorName'),
      contractorContact: data.get('contractorContact'),
    }
    await unsecuredExternalApiRequest(
      `${GATEWAY_URL}/${PLANNER}/labours`,
      HttpMethods.POST,
      body
    )
  },
} satisfies Actions
