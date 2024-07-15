import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { RequestHandler } from './$types'
import { DASHBOARD_URL } from '$lib/scripts/urls'

export const GET = (async (event) => {
  const ret = await unsecuredExternalApiRequest(
    DASHBOARD_URL + '/dashboards/' + event.params.id,
    HttpMethods.GET
  )
  return new Response(ret.body, { status: 200 })
}) satisfies RequestHandler

export const DELETE = (async (event) => {
  const ret = await unsecuredExternalApiRequest(
    DASHBOARD_URL + '/dashboards/' + event.params.id,
    HttpMethods.DELETE
  )
  return new Response(ret.body, { status: 200 })
}) satisfies RequestHandler
