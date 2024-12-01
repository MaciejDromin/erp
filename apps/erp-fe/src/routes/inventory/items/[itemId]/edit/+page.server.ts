import type { PageServerLoad } from './$types'
import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import { INVENTORY_URL } from '$lib/scripts/urls'
import { redirect } from '@sveltejs/kit'

export const actions = {
  default: async ({ cookies, request }) => {
    const data = await request.formData()
    const body = {
      id: data.get('itemId'),
      name: data.get('name'),
      quantity: Number(data.get('quantity')),
      unit: data.get('unit'),
    }
    await unsecuredExternalApiRequest(
      INVENTORY_URL + `/items/${body.id}`,
      HttpMethods.PATCH,
      body
    )
    throw redirect(303, '/inventory/items')
  },
} satisfies Actions

export const load = (async ({ params }) => {
  const item = await unsecuredExternalApiRequest(
    INVENTORY_URL + `/items/${params.itemId}`,
    HttpMethods.GET
  )
  return {
    item: await item.json(),
  }
}) satisfies PageServerLoad
