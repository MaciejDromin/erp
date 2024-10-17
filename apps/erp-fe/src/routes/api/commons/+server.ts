import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods.ts'
import { Services } from '$lib/types/services.ts'
import { FINANCES_URL, INVENTORY_URL } from '$lib/scripts/urls.ts'
import type { RequestHandler } from './$types'

const getBaseUrl = (service) => {
  let ret = ''
  switch (+service) {
    case Services.INVENTORY:
      ret = INVENTORY_URL
      break
    case Services.FINANCES:
      ret = FINANCES_URL
      break
  }
  return ret
}

export const DELETE = (async ({ request }) => {
  const body = await request.json()
  const ret = await unsecuredExternalApiRequest(
    getBaseUrl(body.service) + body.endpoint,
    HttpMethods.DELETE,
    body.content
  )
  return new Response(ret.body, { status: 200 })
}) satisfies RequestHandler
