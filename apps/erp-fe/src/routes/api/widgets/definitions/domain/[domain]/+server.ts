import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { RequestHandler } from './$types'
import { DASHBOARD_URL } from '$lib/scripts/urls'

export const GET = (async (event) => {
  const ret = await unsecuredExternalApiRequest(
    DASHBOARD_URL + '/widgets/definitions/domain/' + event.params.domain,
    HttpMethods.GET
  )
  return new Response(ret.body, { status: 200 })
}) satisfies RequestHandler
