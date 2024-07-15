import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { Actions } from './$types'
import { PLANNER_URL } from '$lib/scripts/urls'

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
      PLANNER_URL + '/planner/labours',
      HttpMethods.POST,
      body
    )
  },
} satisfies Actions
