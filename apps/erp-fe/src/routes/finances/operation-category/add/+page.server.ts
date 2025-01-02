import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { Actions } from './$types'
import { GATEWAY_URL } from '$lib/scripts/urls'
import { FINANCES } from '$lib/scripts/serviceKey.ts'
import { redirect, fail } from '@sveltejs/kit'
import { validate, nonEmpty, lbXnY } from '$lib/scripts/validator.ts'

export const actions = {
  default: async ({ request }) => {
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
    await unsecuredExternalApiRequest(
      `${GATEWAY_URL}/${FINANCES}/operation-category`,
      HttpMethods.POST,
      body
    )
    throw redirect(303, '/finances/operation-category')
  },
} satisfies Actions
