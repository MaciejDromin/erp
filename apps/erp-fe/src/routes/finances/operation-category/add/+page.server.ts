import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { Actions } from './$types'
import { FINANCES_URL } from '$lib/scripts/urls'
import { redirect } from '@sveltejs/kit'

export const actions = {
  default: async ({ request }) => {
    const data = await request.formData()
    const body = {
      operationType: data.get('operationType'),
      operationName: data.get('operationName'),
    }
    await unsecuredExternalApiRequest(
      FINANCES_URL + '/finances/operation-category',
      HttpMethods.POST,
      body
    )
    throw redirect(303, '/finances/operation-category')
  },
} satisfies Actions
