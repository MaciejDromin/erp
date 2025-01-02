import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { RequestHandler } from './$types'
import { GATEWAY_URL } from '$lib/scripts/urls'
import { REPORTS } from '$lib/scripts/serviceKey.ts'

export const POST = (async ({ request }) => {
  const data = await request.json()
  const ret = await unsecuredExternalApiRequest(
    `${GATEWAY_URL}/${REPORTS}/reports/${data.endpoint}`,
    HttpMethods.POST,
    {
      name: data.name,
      template: data.template,
    }
  )
  return new Response(ret.body, { status: 200 })
}) satisfies RequestHandler
