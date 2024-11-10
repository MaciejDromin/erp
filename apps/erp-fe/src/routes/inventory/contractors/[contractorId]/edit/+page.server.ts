import type { PageServerLoad } from './$types'
import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import { INVENTORY_URL } from '$lib/scripts/urls'
import { redirect } from '@sveltejs/kit'

export const actions = {
  default: async ({ cookies, request }) => {
    const data = await request.formData()
    const body = {
      id: data.get('contractorId'),
      name: data.get('name'),
      contactInformation: {
        phoneNumber: data.get('phoneNumber'),
        email: data.get('email'),
        website: data.get('website'),
      },
    }
    await unsecuredExternalApiRequest(
      INVENTORY_URL + `/contractors/${body.id}`,
      HttpMethods.PATCH,
      body
    )
    throw redirect(303, '/inventory/contractors')
  },
} satisfies Actions

export const load = (async ({ params }) => {
  const contractor = await unsecuredExternalApiRequest(
    INVENTORY_URL + `/contractors/${params.contractorId}`,
    HttpMethods.GET
  )
  return {
    contractor: await contractor.json(),
  }
}) satisfies PageServerLoad
