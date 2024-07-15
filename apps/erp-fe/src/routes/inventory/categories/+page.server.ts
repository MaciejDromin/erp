import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { Actions } from './$types'
import { INVENTORY_URL } from '$lib/scripts/urls'

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
  },
} satisfies Actions
