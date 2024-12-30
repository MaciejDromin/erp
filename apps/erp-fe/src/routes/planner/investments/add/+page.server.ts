import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { Actions } from './$types'
import { GATEWAY_URL } from '$lib/scripts/urls'
import { PLANNER } from '$lib/scripts/serviceKey.ts'

export const actions = {
  default: async ({ cookies, request }) => {
    const data = await request.formData()
    const creation = data.get('creation')
    await unsecuredExternalApiRequest(
      `${GATEWAY_URL}/${PLANNER}/investments`,
      HttpMethods.POST,
      JSON.parse(creation?.toString()!)
    )
  },
} satisfies Actions
