import { securedExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { PageServerLoad } from './$types'
import { GATEWAY_URL } from '$lib/scripts/urls'
import { FINANCES, INVENTORY } from '$lib/scripts/serviceKey.ts'
import { ObjectType } from '$lib/finances/types/financialTypes'

export const load = (async ({ params, cookies }) => {
  const objectIds = await securedExternalApiRequest(
    `${GATEWAY_URL}/${FINANCES}/object-value/object-ids?objectType=${ObjectType.VEHICLE}`,
    HttpMethods.GET,
    cookies.get('Authorization'),
    cookies
  )
  if (
    objectIds.status === 204 &&
    objectIds.headers.get('redirected') === 'true'
  )
    throw redirect(303, objectIds.headers.get('location'))
  const objectIdsBody = {
    itemIds: await objectIds.json(),
  }
  const countMap = await securedExternalApiRequest(
    `${GATEWAY_URL}/${INVENTORY}/vehicles/object-count`,
    HttpMethods.POST,
    cookies.get('Authorization'),
    cookies,
    objectIdsBody
  )
  if (countMap.status === 204 && countMap.headers.get('redirected') === 'true')
    throw redirect(303, countMap.headers.get('location'))
  const data = await securedExternalApiRequest(
    `${GATEWAY_URL}/${FINANCES}/object-value/total-value?objectType=${ObjectType.VEHICLE}`,
    HttpMethods.POST,
    cookies.get('Authorization'),
    cookies,
    await countMap.json()
  )
  if (data.status === 204 && data.headers.get('redirected') === 'true')
    throw redirect(303, data.headers.get('location'))
  return {
    objectsValue: await data.json(),
    objectIds: objectIdsBody.itemIds,
  }
}) satisfies PageServerLoad
