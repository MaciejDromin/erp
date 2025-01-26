import { securedExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { RequestHandler } from './$types'
import { GATEWAY_URL } from '$lib/scripts/urls'
import { REPORTS } from '$lib/scripts/serviceKey.ts'

export const POST = (async ({ request, cookies }) => {
  const data = await request.json()
  const ret = await securedExternalApiRequest(
    `${GATEWAY_URL}/${REPORTS}/reports/${data.endpoint}`,
    HttpMethods.POST,
    cookies.get('Authorization'),
    cookies,
    {
      name: data.name,
      template: data.template,
    }
  )
  return new Response(ret.body, { status: ret.status, headers: ret.headers })
}) satisfies RequestHandler
