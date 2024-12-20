import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { RequestHandler } from './$types'
import { DASHBOARD_URL } from '$lib/scripts/urls'

export const POST = (async ({ request }) => {
  const ret = await unsecuredExternalApiRequest(
    DASHBOARD_URL + `/widgets/update-positions`,
    HttpMethods.POST,
    await request.json()
  )
  return new Response(ret.body, { status: 200 })
}) satisfies RequestHandler
