import { securedExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { RequestHandler } from './$types'
import { GATEWAY_URL } from '$lib/scripts/urls'
import { PLANNER } from '$lib/scripts/serviceKey.ts'

export const GET = (async (event) => {
  const ret = await securedExternalApiRequest(
    `${GATEWAY_URL}/${PLANNER}/materials${event.url.search}`,
    HttpMethods.GET,
    event.cookies.get('Authorization'),
    event.cookies
  )
  return new Response(ret.body, { status: ret.status, headers: ret.headers })
}) satisfies RequestHandler
