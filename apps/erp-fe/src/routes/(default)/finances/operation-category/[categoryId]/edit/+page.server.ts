import { securedExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { Actions } from './$types'
import type { PageServerLoad } from './$types'
import { GATEWAY_URL } from '$lib/scripts/urls'
import { FINANCES } from '$lib/scripts/serviceKey.ts'
import { redirect, fail } from '@sveltejs/kit'
import { validate, nonEmpty, lbXnY } from '$lib/scripts/validator.ts'

export const actions = {
  default: async ({ request, cookies }) => {
    const data = await request.formData()
    const body = {
      id: data.get('categoryId'),
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
      `${GATEWAY_URL}/${FINANCES}/operation-category/${body.id}`,
      HttpMethods.PATCH,
      cookies.get('Authorization'),
      cookies,
      body
    )
    if (ret.status === 204 && ret.headers.get("redirected") === "true") throw redirect(303, ret.headers.get("location"))
    throw redirect(303, '/finances/operation-category')
  },
} satisfies Actions

export const load = (async ({ params, cookies }) => {
  const ret = await securedExternalApiRequest(
    `${GATEWAY_URL}/${FINANCES}/operation-category/${params.categoryId}`,
    HttpMethods.GET,
      cookies.get('Authorization'),
      cookies,
  )
  if (ret.status === 204 && ret.headers.get("redirected") === "true") throw redirect(303, ret.headers.get("location"))
  return {
    category: await ret.json(),
  }
}) satisfies PageServerLoad
