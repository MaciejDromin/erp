import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { RequestHandler } from './$types'
import { GATEWAY_URL } from '$lib/scripts/urls'
import { DASHBOARD } from '$lib/scripts/serviceKey.ts'

export const GET = (async (event) => {
  const ret = await unsecuredExternalApiRequest(
    `${GATEWAY_URL}/${DASHBOARD}/dashboards/${event.params.id}`,
    HttpMethods.GET
  )
  return new Response(ret.body, { status: 200 })
}) satisfies RequestHandler

export const DELETE = (async (event) => {
  const ret = await unsecuredExternalApiRequest(
    `${GATEWAY_URL}/${DASHBOARD}/dashboards/${event.params.id}`,
    HttpMethods.DELETE
  )
  return new Response(ret.body, { status: 200 })
}) satisfies RequestHandler
