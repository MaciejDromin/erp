import { securedExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { Actions } from './$types'
import { GATEWAY_URL } from '$lib/scripts/urls'
import { PLANNER } from '$lib/scripts/serviceKey.ts'
import { redirect } from '@sveltejs/kit'

export const actions = {
  default: async ({ cookies, request }) => {
    const data = await request.formData()
    const creation = data.get('creation')
    const ret = await securedExternalApiRequest(
      `${GATEWAY_URL}/${PLANNER}/investments`,
      HttpMethods.POST,
      cookies.get('Authorization'),
      cookies,
      JSON.parse(creation?.toString()!)
    )
    if (ret.status === 204 && ret.headers.get('redirected') === 'true')
      throw redirect(303, ret.headers.get('location'))
  },
} satisfies Actions
