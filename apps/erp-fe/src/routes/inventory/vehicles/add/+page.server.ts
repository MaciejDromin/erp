import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { Actions } from './$types'
import { INVENTORY_URL } from '$lib/scripts/urls'
import { redirect } from '@sveltejs/kit'

export const actions = {
  default: async ({ request }) => {
    const data = await request.formData()
    const body = {
      name: data.get('name'),
      year: data.get('year'),
      odometer: data.get('odometer'),
      bodyStyle: data.get('bodyStyle'),
      make: data.get('make'),
      model: data.get('model'),
      fuelType: data.get('fuelType'),
      driveTrain: data.get('driveTrain'),
      transmission: data.get('transmission'),
      engineType: data.get('engineType'),
      vin: data.get('vin'),
      registrationPlate: data.get('registrationPlate'),
    }
    await unsecuredExternalApiRequest(
      INVENTORY_URL + '/vehicles',
      HttpMethods.POST,
      body
    )
    throw redirect(303, '/inventory/vehicles')
  },
} satisfies Actions
