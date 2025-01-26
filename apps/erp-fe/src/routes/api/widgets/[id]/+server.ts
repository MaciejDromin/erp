import { securedExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { RequestHandler } from './$types'
import { GATEWAY_URL } from '$lib/scripts/urls'
import { DASHBOARD } from '$lib/scripts/serviceKey.ts'

export const POST = (async ({ request, params, cookies }) => {
  const data = await request.json()
  const ret = await securedExternalApiRequest(
    `${GATEWAY_URL}/${DASHBOARD}/widgets/${params.id}`,
    HttpMethods.POST,
    cookies.get('Authorization'),
    cookies,
    data
  )
  return new Response(ret.body, { status: ret.status, headers: ret.headers })
}) satisfies RequestHandler
