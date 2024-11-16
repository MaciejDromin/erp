import type { PageServerLoad } from './$types'
import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import { INVENTORY_URL } from '$lib/scripts/urls'
import { redirect } from '@sveltejs/kit'

export const actions = {
  default: async ({ cookies, request }) => {
    const data = await request.formData()
    const manufacturer = JSON.parse(data.get('manufacturer'))
    const body = {
      id: data.get('partId'),
      name: data.get('name'),
      partNumber: data.get('partNumber'),
      manufacturerId: manufacturer === null ? null : manufacturer.id,
    }
    await unsecuredExternalApiRequest(
      INVENTORY_URL + `/parts/${body.id}`,
      HttpMethods.PATCH,
      body
    )
    throw redirect(303, '/inventory/parts')
  },
} satisfies Actions

export const load = (async ({ params }) => {
  const part = await unsecuredExternalApiRequest(
    INVENTORY_URL + `/parts/${params.partId}`,
    HttpMethods.GET
  )
  return {
    part: await part.json(),
  }
}) satisfies PageServerLoad
