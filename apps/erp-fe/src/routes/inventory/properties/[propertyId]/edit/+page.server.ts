import type { PageServerLoad } from './$types'
import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import { INVENTORY_URL } from '$lib/scripts/urls'
import { redirect } from '@sveltejs/kit'

export const actions = {
  default: async ({ cookies, request }) => {
    const data = await request.formData()
    const address = JSON.parse(data.get('address'))
    const body = {
      id: data.get('propertyId'),
      name: data.get('name'),
      uniqueIdentifier: data.get('uniqueIdentifier'),
      addressId: address.id,
      landRegister: data.get('landRegister'),
      propertyInformation: {
        propertyType: data.get('propertyType'),
        landClassification: data.get('landClassification'),
        landArea: {
          area: Number(data.get('area')),
          unit: data.get('areaUnit'),
        },
      },
    }
    await unsecuredExternalApiRequest(
      INVENTORY_URL + `/properties/${body.id}`,
      HttpMethods.PATCH,
      body
    )
    throw redirect(303, '/inventory/properties')
  },
} satisfies Actions

export const load = (async ({ params }) => {
  const property = await unsecuredExternalApiRequest(
    INVENTORY_URL + `/properties/${params.propertyId}`,
    HttpMethods.GET
  )
  return {
    property: await property.json(),
  }
}) satisfies PageServerLoad
