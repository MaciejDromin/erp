import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { Actions } from './$types'
import { INVENTORY_URL } from '$lib/scripts/urls'
import { redirect } from '@sveltejs/kit'

export const actions = {
  default: async ({ request }) => {
    const data = await request.formData()
    const contractor = JSON.parse(data.get('contractor'))
    const body = {
      date: data.get('date'),
      odometer: data.get('odometer'),
      parts: JSON.parse(data.get('parts')),
      contractorId: contractor.id,
    }
    await unsecuredExternalApiRequest(
      INVENTORY_URL + '/maintenance',
      HttpMethods.POST,
      body
    )
    throw redirect(303, '/inventory/maintenance')
  },
} satisfies Actions
