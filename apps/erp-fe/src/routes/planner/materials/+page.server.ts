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
      unitAmount: {
        value: data.get('value'),
        currencyCode: data.get('currency'),
      },
      source: data.get('source'),
    }
    await unsecuredExternalApiRequest(
      `${GATEWAY_URL}/${PLANNER}/materials`,
      HttpMethods.POST,
      body
    )
  },
} satisfies Actions
