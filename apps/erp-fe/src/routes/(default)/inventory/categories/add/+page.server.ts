import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { Actions } from './$types'
import { GATEWAY_URL } from '$lib/scripts/urls'
import { INVENTORY } from '$lib/scripts/serviceKey.ts'
import { redirect, fail } from '@sveltejs/kit'
import { validate, nonEmpty, lbXnY } from '$lib/scripts/validator.ts'

export const actions = {
  default: async ({ cookies, request }) => {
    const data = await request.formData()
    const body = {
      name: data.get('name'),
    }
    let validation = validate(body.name, nonEmpty, lbXnY(3, 16))
    if (!validation.result) {
      return fail(422, {
        id: {
          val: undefined,
          message: undefined,
        },
        name: {
          val: body.name,
          message: validation.message,
        },
      })
    }
    await unsecuredExternalApiRequest(
      `${GATEWAY_URL}/${INVENTORY}/categories`,
      HttpMethods.POST,
      body
    )
    throw redirect(303, '/inventory/categories')
  },
} satisfies Actions
