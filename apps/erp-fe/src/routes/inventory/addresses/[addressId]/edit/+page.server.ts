import type { PageServerLoad } from './$types'
import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import { INVENTORY_URL } from '$lib/scripts/urls'
import { redirect } from '@sveltejs/kit'

export const actions = {
  default: async ({ cookies, request }) => {
    const data = await request.formData()
    const body = {
      id: data.get('addressId'),
      addressLine: data.get('addressLine'),
      city: data.get('city'),
      postalCode: data.get('postalCode'),
      province: data.get('province'),
      country: data.get('country'),
    }
    await unsecuredExternalApiRequest(
      INVENTORY_URL + `/addresses/${body.id}`,
      HttpMethods.PATCH,
      body
    )
    throw redirect(303, '/inventory/addresses')
  },
} satisfies Actions

export const load = (async ({ params }) => {
  const address = await unsecuredExternalApiRequest(
    INVENTORY_URL + `/addresses/${params.addressId}`,
    HttpMethods.GET
  )
  return {
    address: await address.json(),
  }
}) satisfies PageServerLoad
