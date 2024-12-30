import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { RequestHandler } from './$types'
import { GATEWAY_URL } from '$lib/scripts/urls'
import { DASHBOARD } from '$lib/scripts/serviceKey.ts'

export const DELETE = (async ({ params }) => {
  const ret = await unsecuredExternalApiRequest(
    `${GATEWAY_URL}/${DASHBOARD}/dashboards/${params.id}/widgets/${params.widgetId}`,
    HttpMethods.DELETE
  )
  return new Response(ret.body, { status: 200 })
}) satisfies RequestHandler
