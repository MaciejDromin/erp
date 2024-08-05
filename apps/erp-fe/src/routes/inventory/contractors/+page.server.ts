import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { Actions } from './$types'
import { INVENTORY_URL } from '$lib/scripts/urls'

export const actions = {
  default: async ({ request }) => {
    const data = await request.formData()
    const body = {
      name: data.get('name'),
      contactInformation: {
        phoneNumber: data.get('phoneNumber'),
        email: data.get('email'),
        website: data.get('website'),
      },
    }
    await unsecuredExternalApiRequest(
      INVENTORY_URL + '/contractors',
      HttpMethods.POST,
      body
    )
  },
} satisfies Actions
