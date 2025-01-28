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
  let data = await ret.json()
  data.current = cookies.get('X-OrgId')
  return new Response(JSON.stringify(data), { status: ret.status })
}) satisfies RequestHandler
