import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { Actions } from './$types'
import { INVENTORY_URL } from '$lib/scripts/urls'

export const actions = {
  default: async ({ request }) => {
    const data = await request.formData()
    const body = {
      name: data.get('name'),
      uniqueIdentifier: data.get('uniqueIdentifier'),
      addressId: data.get('addressId'),
      propertyInformation: {
        propertyType: data.get('propertyType'),
        landClassification: data.get('landClassification'),
        landArea: {
          area: parseInt(parseFloat(data.get('area')) * 100),
          unit: data.get('areaUnit'),
        },
      },
    }
    console.log(body)
    let test = await unsecuredExternalApiRequest(
      INVENTORY_URL + '/properties',
      HttpMethods.POST,
      body
    )
    console.log(test)
  },
} satisfies Actions