import { securedExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { Actions } from './$types'
import { GATEWAY_URL } from '$lib/scripts/urls'
import { FINANCES } from '$lib/scripts/serviceKey.ts'
import { redirect, fail } from '@sveltejs/kit'
import { validate, nonEmpty, lbXnY } from '$lib/scripts/validator.ts'

export const actions = {
  default: async ({ request, cookies }) => {
    const data = await request.formData()
    const body = {
      operationType: data.get('operationType'),
      operationName: data.get('operationName'),
    }
    let validation = validate(body.operationName, nonEmpty, lbXnY(3, 16))
    if (!validation.result) {
      return fail(422, {
        id: {
          val: undefined,
          message: undefined,
        },
        operationName: {
          val: body.operationName,
          message: validation.message,
        },
        operationType: {
          val: body.operationType,
          message: undefined,
        },
      })
    }
    const ret = await securedExternalApiRequest(
      `${GATEWAY_URL}/${FINANCES}/operation-category`,
      HttpMethods.POST,
      cookies.get('Authorization'),
      cookies,
      body
    )
    if (ret.status === 204 && ret.headers.get('redirected') === 'true')
      redirect(303, ret.headers.get('location'));
    redirect(303, '/finances/operation-category');
  },
} satisfies Actions
