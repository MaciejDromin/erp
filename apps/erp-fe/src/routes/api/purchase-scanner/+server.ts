import { unsecuredExternalApiRequestFileUpload } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import { GATEWAY_URL } from '$lib/scripts/urls.ts'
import { PURCHASE_SCANNER } from '$lib/scripts/serviceKey.ts'
import type { RequestHandler } from './$types'

export const POST = (async ({ request }) => {
  const headers = {
    Accept: 'application/json',
    'Content-Type': 'application/octet-stream',
    filename: request.headers.get('filename'),
  }
  const ret = await unsecuredExternalApiRequestFileUpload(
    `${GATEWAY_URL}/${PURCHASE_SCANNER}/receipts`,
    HttpMethods.POST,
    request.body,
    headers
  )
  return new Response(ret.body, { status: 200 })
}) satisfies RequestHandler
