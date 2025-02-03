import { securedExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { Actions } from './$types'
import { GATEWAY_URL } from '$lib/scripts/urls'
import { PLANNER } from '$lib/scripts/serviceKey.ts'
import { redirect } from '@sveltejs/kit'

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
    const ret = await securedExternalApiRequest(
      `${GATEWAY_URL}/${PLANNER}/labours`,
      HttpMethods.POST,
      cookies.get('Authorization'),
      cookies,
      body
    )
    if (ret.status === 204 && ret.headers.get('redirected') === 'true')
      redirect(303, ret.headers.get('location'));
  },
} satisfies Actions
