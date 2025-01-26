import { securedExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { RequestHandler } from './$types'

export const POST = (async ({ request, cookies }) => {
  const data = await request.json()
  let query = ''
  if (data.filters !== undefined) {
    query = '?'
    for (const [key, value] of Object.entries(data.filters)) {
      query += `${key}=${value}&`
    }
    query = query.substring(0, query.length - 1)
  }
  const ret = await securedExternalApiRequest(
    data.datasource + query,
    cookies.get('Authorization'),
    cookies,
    HttpMethods.GET
  )
  return new Response(ret.body, { status: ret.status, headers: ret.headers })
}) satisfies RequestHandler
