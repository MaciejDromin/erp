import { securedExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { RequestHandler } from './$types'
import { GATEWAY_URL } from '$lib/scripts/urls'
import { DASHBOARD } from '$lib/scripts/serviceKey.ts'

export const GET = (async (event) => {
  const ret = await securedExternalApiRequest(
    `${GATEWAY_URL}/${DASHBOARD}/dashboards/${event.params.id}`,
    event.cookies.get('Authorization'),
    event.cookies,
    HttpMethods.GET
  )
  return new Response(ret.body, { status: ret.status, headers: ret.headers })
}) satisfies RequestHandler

export const DELETE = (async (event) => {
  const ret = await securedExternalApiRequest(
    `${GATEWAY_URL}/${DASHBOARD}/dashboards/${event.params.id}`,
    event.cookies.get('Authorization'),
    event.cookies,
    HttpMethods.DELETE
  )
  return new Response(ret.body, { status: ret.status, headers: ret.headers })
}) satisfies RequestHandler
