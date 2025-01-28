import { securedExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { RequestHandler } from './$types'
import { AUTH_URL } from '$lib/scripts/urls'

export const GET = (async ({ cookies }) => {
  const ret = await securedExternalApiRequest(
    `${AUTH_URL}/get-orgs`,
    HttpMethods.GET,
    cookies.get('Authorization'),
    cookies
  )
  return new Response(ret.body, { status: ret.status, headers: ret.headers })
}) satisfies RequestHandler
