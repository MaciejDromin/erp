import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { RequestHandler } from './$types'
import { GATEWAY_URL } from '$lib/scripts/urls'
import { DASHBOARD } from '$lib/scripts/serviceKey.ts'

export const POST = (async ({ request, params }) => {
  const ret = await unsecuredExternalApiRequest(
    `${GATEWAY_URL}/${DASHBOARD}/widgets/${params.id}/filters`,
    HttpMethods.POST,
    await request.json()
  )
  return new Response(ret.body, { status: 200 })
}) satisfies RequestHandler
