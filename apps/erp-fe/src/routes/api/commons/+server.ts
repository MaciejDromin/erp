import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests'
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

export const DELETE = (async ({ request }) => {
  const body = await request.json()
  const ret = await unsecuredExternalApiRequest(
    `${GATEWAY_URL}/${getBaseUrl(body.service)}${body.endpoint}`,
    HttpMethods.DELETE,
    body.content
  )
  return new Response(ret.body, { status: 200 })
}) satisfies RequestHandler
