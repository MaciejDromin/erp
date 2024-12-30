import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { RequestHandler } from './$types'
import { GATEWAY_URL } from '$lib/scripts/urls'
import { FINANCES } from '$lib/scripts/serviceKey.ts'

export const GET = (async (event) => {
  const ret = await unsecuredExternalApiRequest(
    `${GATEWAY_URL}/${FINANCES}/operation-category${event.url.search}`,
    HttpMethods.GET
  )
  return new Response(ret.body, { status: 200 })
}) satisfies RequestHandler
