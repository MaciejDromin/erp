import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { PageServerLoad } from './$types'
import { GATEWAY_URL } from '$lib/scripts/urls'
import { FINANCES, INVENTORY } from '$lib/scripts/serviceKey.ts'
import { ObjectType } from '$lib/finances/types/financialTypes'

export const load = (async ({ params }) => {
  const objectIds = await unsecuredExternalApiRequest(
    `${GATEWAY_URL}/${FINANCES}/object-value/object-ids?objectType=${ObjectType.ITEM}`,
    HttpMethods.GET
  )
  const objectIdsBody = {
    itemIds: await objectIds.json(),
  }
  const countMap = await unsecuredExternalApiRequest(
    `${GATEWAY_URL}/${INVENTORY}/items/object-count`,
    HttpMethods.POST,
    objectIdsBody
  )
  const data = await unsecuredExternalApiRequest(
    `${GATEWAY_URL}/${FINANCES}/object-value/total-value?objectType=${ObjectType.ITEM}`,
    HttpMethods.POST,
    await countMap.json()
  )
  return {
    objectsValue: await data.json(),
    objectIds: objectIdsBody.itemIds,
  }
}) satisfies PageServerLoad
