import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { RequestHandler } from './$types'
import { DASHBOARD_URL } from '$lib/scripts/urls'

export const POST = (async ({ request, params }) => {
  const ret = await unsecuredExternalApiRequest(
    DASHBOARD_URL + `/widgets/${params.id}/filters`,
    HttpMethods.POST,
    await request.json()
  )
  return new Response(ret.body, { status: 200 })
}) satisfies RequestHandler
