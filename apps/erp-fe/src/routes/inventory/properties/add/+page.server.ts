import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { Actions } from './$types'
import { INVENTORY_URL } from '$lib/scripts/urls'
import { redirect } from '@sveltejs/kit'

export const actions = {
  default: async ({ request }) => {
    const data = await request.formData()
    const address = JSON.parse(data.get('address'))
    const body = {
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
      INVENTORY_URL + '/properties',
      HttpMethods.POST,
      body
    )
    throw redirect(303, '/inventory/properties')
  },
} satisfies Actions
