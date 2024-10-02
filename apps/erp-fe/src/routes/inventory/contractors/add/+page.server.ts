import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { Actions } from './$types'
import { INVENTORY_URL } from '$lib/scripts/urls'
import { redirect } from '@sveltejs/kit'

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
    throw redirect(303, '/inventory/contractors')
  },
} satisfies Actions
