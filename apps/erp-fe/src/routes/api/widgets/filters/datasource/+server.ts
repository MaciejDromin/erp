import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { RequestHandler } from './$types'
import { DASHBOARD_URL } from '$lib/scripts/urls'

export const POST = (async ({ request }) => {
  const data = await request.json()
  let query = ''
  if (data.filters !== undefined) {
    query = '?'
    for (const [key, value] of Object.entries(data.filters)) {
      query += `${key}=${value}&`
    }
    query = query.substring(0, query.length - 1)
  }
  const ret = await unsecuredExternalApiRequest(
    data.datasource + query,
    HttpMethods.GET
  )
  return new Response(ret.body, { status: 200 })
}) satisfies RequestHandler
