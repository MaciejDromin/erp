import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { Actions } from './$types'
import { INVENTORY_URL } from '$lib/scripts/urls'
import { redirect } from '@sveltejs/kit'

export const actions = {
  default: async ({ request }) => {
    const data = await request.formData()
    const category = JSON.parse(data.get('category'))
    const body = {
      name: data.get('name'),
      quantity: data.get('quantity'),
      unit: data.get('unit'),
      categoryIds: [category.id],
    }
    await unsecuredExternalApiRequest(
      INVENTORY_URL + '/items',
      HttpMethods.POST,
      body
    )
    throw redirect(303, '/inventory/items')
  },
} satisfies Actions
