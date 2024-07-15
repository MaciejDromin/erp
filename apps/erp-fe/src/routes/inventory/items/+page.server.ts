import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { Actions } from './$types'
import { INVENTORY_URL } from '$lib/scripts/urls'

export const actions = {
  default: async ({ request }) => {
    const data = await request.formData()
    const body = {
      name: data.get('name'),
      quantity: data.get('quantity'),
      unit: data.get('unit'),
      categoryIds: data.getAll('categoryIds'),
    }
    await unsecuredExternalApiRequest(
      INVENTORY_URL + '/inventory/items',
      HttpMethods.POST,
      body
    )
  },
} satisfies Actions
