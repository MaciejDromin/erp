import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { RequestHandler } from './$types'

export const POST = (async ({ request }) => {
  const data = await request.json()
  const ret = await unsecuredExternalApiRequest(
    data.url,
    HttpMethods.POST,
    // TODO: Pass context
  )
  return new Response(ret.body, { status: 200 })
}) satisfies RequestHandler
