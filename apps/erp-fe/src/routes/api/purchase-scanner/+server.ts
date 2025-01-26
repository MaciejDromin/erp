import { securedExternalApiRequestFileUpload } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import { GATEWAY_URL } from '$lib/scripts/urls.ts'
import { PURCHASE_SCANNER } from '$lib/scripts/serviceKey.ts'
import type { RequestHandler } from './$types'

export const POST = (async ({ request, cookies }) => {
  const headers = {
    Accept: 'application/json',
    'Content-Type': 'application/octet-stream',
    filename: request.headers.get('filename'),
  }
  const ret = await securedExternalApiRequestFileUpload(
    `${GATEWAY_URL}/${PURCHASE_SCANNER}/receipts`,
    HttpMethods.POST,
    headers,
    cookies.get('Authorization'),
    cookies,
    request.body
  )
  return new Response(ret.body, { status: ret.status, headers: ret.headers })
}) satisfies RequestHandler
