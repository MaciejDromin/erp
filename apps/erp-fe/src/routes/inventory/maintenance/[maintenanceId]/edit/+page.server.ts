import type { PageServerLoad } from './$types'
import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import { INVENTORY_URL } from '$lib/scripts/urls'
import { redirect } from '@sveltejs/kit'

export const actions = {
  default: async ({ cookies, request }) => {
    const data = await request.formData()
    const contractor = JSON.parse(data.get('contractor'))
    const body = {
      id: data.get('maintenanceId'),
      date: data.get('date'),
      odometer: Number(data.get('odometer')),
      parts: JSON.parse(data.get('parts')),
      contractorId: contractor.id,
    }
    await unsecuredExternalApiRequest(
      INVENTORY_URL + `/maintenance/${body.id}`,
      HttpMethods.PATCH,
      body
    )
    throw redirect(303, '/inventory/maintenance')
  },
} satisfies Actions

export const load = (async ({ params }) => {
  let maintenance = await unsecuredExternalApiRequest(
    INVENTORY_URL + `/maintenance/${params.maintenanceId}`,
    HttpMethods.GET
  )
  maintenance = await maintenance.json()
  const partsIds = maintenance.parts.map(p => p.id);
  let partsData = await unsecuredExternalApiRequest(
    INVENTORY_URL + `/parts?partIds=${partsIds.join(',')}&size=500`,
    HttpMethods.GET
  )
  return {
    maintenance: maintenance,
    parts: (await partsData.json()).content,
  }
}) satisfies PageServerLoad
