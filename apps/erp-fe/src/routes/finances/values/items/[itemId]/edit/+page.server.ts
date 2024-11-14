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
      uuid: data.get('objectValueId'),
      amount: {
        value: data.get('amount'),
        currencyCode: data.get('currencyCode'),
      },
      objectId: object.id,
    }
    await unsecuredExternalApiRequest(
      FINANCES_URL + `/finances/object-value/${body.uuid}`,
      HttpMethods.PATCH,
      body
    )
    throw redirect(303, '/finances/values/items')
  },
} satisfies Actions

export const load = (async ({ params }) => {
  const objectIds = await unsecuredExternalApiRequest(
    FINANCES_URL +
      `/finances/object-value/object-ids?objectType=${ObjectType.ITEM}`,
    HttpMethods.GET
  )
  const ov = await unsecuredExternalApiRequest(
    FINANCES_URL + `/finances/object-value/${params.itemId}`,
    HttpMethods.GET
  )
  return {
    objectIds: await objectIds.json(),
    objectValue: await ov.json(),
  }
}) satisfies PageServerLoad
