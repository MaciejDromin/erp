import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { Actions } from './$types'
import { INVENTORY_URL } from '$lib/scripts/urls'
import { redirect } from '@sveltejs/kit'

export const actions = {
  default: async ({ request }) => {
    const data = await request.formData()
    const manufacturer = JSON.parse(data.get('manufacturer'))
    const body = {
      name: data.get('name'),
      partNumber: data.get('partNumber'),
      manufacturerId: manufacturer.id,
    }
    await unsecuredExternalApiRequest(
      INVENTORY_URL + '/parts',
      HttpMethods.POST,
      body
    )
    throw redirect(303, '/inventory/parts')
  },
} satisfies Actions
