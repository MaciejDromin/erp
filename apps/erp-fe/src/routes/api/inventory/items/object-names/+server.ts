import { securedExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { RequestHandler } from './$types'
import { GATEWAY_URL } from '$lib/scripts/urls'
import { INVENTORY } from '$lib/scripts/serviceKey.ts'

export const POST = (async ({ request, cookies }) => {
  const ret = await securedExternalApiRequest(
    `${GATEWAY_URL}/${INVENTORY}/items/object-names`,
    HttpMethods.POST,
    cookies.get('Authorization'),
    cookies,
    await request.json()
  )
  return new Response(ret.body, { status: ret.status, headers: ret.headers })
}) satisfies RequestHandler
