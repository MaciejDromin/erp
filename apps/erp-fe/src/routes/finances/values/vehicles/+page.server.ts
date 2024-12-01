import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { PageServerLoad } from './$types'
import { FINANCES_URL, INVENTORY_URL } from '$lib/scripts/urls'
import { ObjectType } from '$lib/finances/types/financialTypes'

export const load = (async ({ params }) => {
  const objectIds = await unsecuredExternalApiRequest(
    FINANCES_URL + `/object-value/object-ids?objectType=${ObjectType.VEHICLE}`,
    HttpMethods.GET
  )
  const objectIdsBody = {
    itemIds: await objectIds.json(),
  }
  const countMap = await unsecuredExternalApiRequest(
    INVENTORY_URL + '/vehicles/object-count',
    HttpMethods.POST,
    objectIdsBody
  )
  const data = await unsecuredExternalApiRequest(
    FINANCES_URL + `/object-value/total-value?objectType=${ObjectType.VEHICLE}`,
    HttpMethods.POST,
    await countMap.json()
  )
  return {
    objectsValue: await data.json(),
    objectIds: objectIdsBody.itemIds,
  }
}) satisfies PageServerLoad
