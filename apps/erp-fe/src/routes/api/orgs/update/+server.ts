import { securedExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { RequestHandler } from './$types'
import { AUTH_URL } from '$lib/scripts/urls'

export const POST = (async (event) => {
  const ret = await securedExternalApiRequest(
    `${AUTH_URL}/current-org-update${event.url.search}`,
    HttpMethods.POST,
    event.cookies.get('Authorization'),
    event.cookies
  )
  const neverExp = new Date()
  neverExp.setDate(neverExp.getDate() + 10 * 365)
  const opts = {
    path: '/',
    expires: neverExp,
  }
  event.cookies.set('X-OrgId', event.url.search.substring(10), opts)
  return new Response(null, {
    status: 204,
    headers: { redirected: 'true', location: '/' },
  })
}) satisfies RequestHandler
