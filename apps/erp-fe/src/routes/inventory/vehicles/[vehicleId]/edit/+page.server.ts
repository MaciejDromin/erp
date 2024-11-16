import type { PageServerLoad } from './$types'
import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import { INVENTORY_URL } from '$lib/scripts/urls'
import { redirect } from '@sveltejs/kit'

export const actions = {
  default: async ({ cookies, request }) => {
    const data = await request.formData()
    const body = {
      id: data.get('vehicleId'),
      name: data.get('name'),
      year: Number(data.get('year')),
      odometer: Number(data.get('odometer')),
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
      INVENTORY_URL + `/vehicles/${body.id}`,
      HttpMethods.PATCH,
      body
    )
    throw redirect(303, '/inventory/vehicles')
  },
} satisfies Actions

export const load = (async ({ params }) => {
  const vehicle = await unsecuredExternalApiRequest(
    INVENTORY_URL + `/vehicles/${params.vehicleId}`,
    HttpMethods.GET
  )
  return {
    vehicle: await vehicle.json(),
  }
}) satisfies PageServerLoad
