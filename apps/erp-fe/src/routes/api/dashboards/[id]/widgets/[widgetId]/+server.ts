import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { RequestHandler } from './$types'
import { DASHBOARD_URL } from '$lib/scripts/urls'

export const DELETE = (async ({ params }) => {
  const ret = await unsecuredExternalApiRequest(
    DASHBOARD_URL + `/dashboards/${params.id}/widgets/${params.widgetId}`,
    HttpMethods.DELETE
  )
  return new Response(ret.body, { status: 200 })
}) satisfies RequestHandler
