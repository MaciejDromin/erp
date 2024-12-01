import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { Actions } from './$types'
import type { PageServerLoad } from './$types'
import { FINANCES_URL, INVENTORY_URL } from '$lib/scripts/urls'
import { ObjectType } from '$lib/finances/types/financialTypes'
import { redirect } from '@sveltejs/kit'

export const actions = {
  default: async ({ request }) => {
    const data = await request.formData()
    const object = JSON.parse(data.get('object'))
    const body = {
      amount: data.get('amount'),
      currencyCode: data.get('currencyCode'),
      objectId: object.id,
      objectType: ObjectType.VEHICLE,
    }
    await unsecuredExternalApiRequest(
      FINANCES_URL + '/object-value',
      HttpMethods.POST,
      body
    )
    throw redirect(303, '/finances/values/vehicles')
  },
} satisfies Actions

export const load = (async ({ params }) => {
  const objectIds = await unsecuredExternalApiRequest(
    FINANCES_URL +
      `/object-value/object-ids?objectType=${ObjectType.VEHICLE}`,
    HttpMethods.GET
  )
  return {
    objectIds: await objectIds.json(),
  }
}) satisfies PageServerLoad
