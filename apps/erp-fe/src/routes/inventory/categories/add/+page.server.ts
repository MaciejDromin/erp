import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { Actions } from './$types'
import { INVENTORY_URL } from '$lib/scripts/urls'
import { redirect } from '@sveltejs/kit'

export const actions = {
  default: async ({ cookies, request }) => {
    const data = await request.formData()
    const body = {
      name: data.get('name'),
    }
    await unsecuredExternalApiRequest(
      INVENTORY_URL + '/inventory/categories',
      HttpMethods.POST,
      body
    )
    throw redirect(303, '/inventory/categories')
  },
} satisfies Actions
