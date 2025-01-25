import { securedExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { RequestHandler } from './$types'
import { GATEWAY_URL } from '$lib/scripts/urls'
import { DASHBOARD } from '$lib/scripts/serviceKey.ts'

export const DELETE = (async ({ params, cookies }) => {
  const ret = await securedExternalApiRequest(
    `${GATEWAY_URL}/${DASHBOARD}/dashboards/${params.id}/widgets/${params.widgetId}`,
    cookies.get('Authorization'),
    cookies,
    HttpMethods.DELETE
  )
  return new Response(ret.body, { status: ret.status, headers: ret.headers })
}) satisfies RequestHandler
