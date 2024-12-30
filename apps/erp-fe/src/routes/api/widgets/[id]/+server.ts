import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { RequestHandler } from './$types'
import { GATEWAY_URL } from '$lib/scripts/urls'
import { DASHBOARD } from '$lib/scripts/serviceKey.ts'

export const POST = (async ({ request, params }) => {
  const data = await request.json()
  const ret = await unsecuredExternalApiRequest(
    `${GATEWAY_URL}/${DASHBOARD}/widgets/${params.id}`,
    HttpMethods.POST,
    data
  )
  return new Response(ret.body, { status: 200 })
}) satisfies RequestHandler
