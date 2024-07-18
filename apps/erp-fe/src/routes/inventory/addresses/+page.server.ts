import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { Actions } from './$types'
import { INVENTORY_URL } from '$lib/scripts/urls'

export const actions = {
  default: async ({ cookies, request }) => {
    const data = await request.formData()
    const body = {
      addressLine: data.get('addressLine'),
      city: data.get('city'),
      country: data.get('country'),
      province: data.get('province'),
      postalCode: data.get('postalCode'),
    }
    await unsecuredExternalApiRequest(
      INVENTORY_URL + '/addresses',
      HttpMethods.POST,
      body
    )
  },
} satisfies Actions
