import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { Actions } from './$types'
import { FINANCES_URL } from '$lib/scripts/urls'
import { redirect, fail } from '@sveltejs/kit'
import { validate, nonEmpty, lb3n16 } from '$lib/scripts/validator.ts'

export const actions = {
  default: async ({ request }) => {
    const data = await request.formData()
    const body = {
      operationType: data.get('operationType'),
      operationName: data.get('operationName'),
    }
    let validation = validate(body.operationName, nonEmpty, lb3n16)
    if (!validation.result) {
      return fail(422, {
        operationName: validation.message,
      })
    }
    await unsecuredExternalApiRequest(
      FINANCES_URL + '/operation-category',
      HttpMethods.POST,
      body
    )
    throw redirect(303, '/finances/operation-category')
  },
} satisfies Actions
