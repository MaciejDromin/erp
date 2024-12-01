import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { Actions } from './$types'
import type { PageServerLoad } from './$types'
import { FINANCES_URL } from '$lib/scripts/urls'
import { ObjectType } from '$lib/finances/types/financialTypes'
import { redirect } from '@sveltejs/kit'

export const actions = {
  default: async ({ request }) => {
    const data = await request.formData()
    const object = JSON.parse(data.get('object'))
    const body = {
      id: data.get('objectValueId'),
      amount: {
        value: data.get('amount'),
        currencyCode: data.get('currencyCode'),
      },
      objectId: object.id,
    }
    await unsecuredExternalApiRequest(
      FINANCES_URL + `/object-value/${body.id}`,
      HttpMethods.PATCH,
      body
    )
    throw redirect(303, '/finances/values/properties')
  },
} satisfies Actions

export const load = (async ({ params }) => {
  const objectIds = await unsecuredExternalApiRequest(
    FINANCES_URL + `/object-value/object-ids?objectType=${ObjectType.PROPERTY}`,
    HttpMethods.GET
  )
  const ov = await unsecuredExternalApiRequest(
    FINANCES_URL + `/object-value/${params.propertyId}`,
    HttpMethods.GET
  )
  return {
    objectIds: await objectIds.json(),
    objectValue: await ov.json(),
  }
}) satisfies PageServerLoad
