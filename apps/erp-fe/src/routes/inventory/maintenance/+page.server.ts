import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { Actions } from './$types'
import { INVENTORY_URL } from '$lib/scripts/urls'

export const actions = {
  default: async ({ request }) => {
    const data = await request.formData()
    const body = {
      date: data.get('date'),
      odometer: data.get('odometer'),
      parts: JSON.parse(data.get('parts')),
      contractorId: data.get('contractorId'),
    }
    console.log(body)
    let test = await unsecuredExternalApiRequest(
      INVENTORY_URL + '/maintenance',
      HttpMethods.POST,
      body
    )
    console.log(test)
  },
} satisfies Actions
