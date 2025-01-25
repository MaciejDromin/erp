import { securedExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods.ts'
import { Services } from '$lib/types/services.ts'
import { GATEWAY_URL } from '$lib/scripts/urls.ts'
import { INVENTORY, FINANCES } from '$lib/scripts/serviceKey.ts'
import type { RequestHandler } from './$types'

const getBaseUrl = (service) => {
  let ret = ''
  switch (+service) {
    case Services.INVENTORY:
      ret = INVENTORY
      break
    case Services.FINANCES:
      ret = FINANCES
      break
  }
  return ret
}

export const DELETE = (async ({ request, cookies }) => {
  const body = await request.json()
  const ret = await securedExternalApiRequest(
    `${GATEWAY_URL}${body.endpoint}`,
    HttpMethods.DELETE,
    cookies.get('Authorization'),
    cookies,
    body.content
  )
  return new Response(ret.body, { status: ret.status, headers: ret.headers })
}) satisfies RequestHandler
