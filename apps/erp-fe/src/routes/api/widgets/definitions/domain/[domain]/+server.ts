import { securedExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { RequestHandler } from './$types'
import { GATEWAY_URL } from '$lib/scripts/urls'
import { DASHBOARD } from '$lib/scripts/serviceKey.ts'

export const GET = (async (event) => {
  const ret = await securedExternalApiRequest(
    `${GATEWAY_URL}/${DASHBOARD}/widgets/definitions/domain/${event.params.domain}`,
    HttpMethods.GET,
    event.cookies.get('Authorization'),
    event.cookies
  )
  return new Response(ret.body, { status: ret.status, headers: ret.headers })
}) satisfies RequestHandler
