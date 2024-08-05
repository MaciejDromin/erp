import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { Actions } from './$types'
import { INVENTORY_URL } from '$lib/scripts/urls'

export const actions = {
  default: async ({ request }) => {
    const data = await request.formData()
    const body = {
      name: data.get('name'),
      partNumber: data.get('partNumber'),
      manufacturerId: data.get('manufacturerId'),
    }
    await unsecuredExternalApiRequest(
      INVENTORY_URL + '/parts',
      HttpMethods.POST,
      body
    )
  },
} satisfies Actions
