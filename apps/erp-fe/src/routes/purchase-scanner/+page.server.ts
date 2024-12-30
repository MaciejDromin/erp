import { unsecuredExternalApiRequestFileUpload } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { Actions } from './$types'
import { GATEWAY_URL } from '$lib/scripts/urls'
import { PURCHASE_SCANNER } from '$lib/scripts/serviceKey.ts'

export const actions = {
  default: async ({ request }) => {
    const data = await request.formData()
    const body = new FormData()
    body.append('file', data.get('file'))
    const headers = {
      Accept: 'application/json',
    }
    const ret = await unsecuredExternalApiRequestFileUpload(
      `${GATEWAY_URL}/${PURCHASE_SCANNER}/receipts`,
      HttpMethods.POST,
      body,
      headers
    )
  },
} satisfies Actions
