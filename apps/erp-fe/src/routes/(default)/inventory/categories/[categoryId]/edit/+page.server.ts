import type { PageServerLoad } from './$types'
import { securedExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import { GATEWAY_URL } from '$lib/scripts/urls'
import { INVENTORY } from '$lib/scripts/serviceKey.ts'
import { redirect, fail } from '@sveltejs/kit'
import { validate, nonEmpty, lbXnY } from '$lib/scripts/validator.ts'

export const actions = {
  default: async ({ cookies, request }) => {
    const data = await request.formData()
    const body = {
      id: data.get('categoryId'),
      name: data.get('name'),
    }
    let validation = validate(body.name, nonEmpty, lbXnY(3, 16))
    if (!validation.result) {
      return fail(422, {
        id: {
          val: body.id,
          message: undefined,
        },
        name: {
          val: body.name,
          message: validation.message,
        },
      })
    }
    const ret = await securedExternalApiRequest(
      `${GATEWAY_URL}/${INVENTORY}/categories/${body.id}`,
      HttpMethods.PATCH,
      cookies.get('Authorization'),
      cookies,
      body
    )
    if (ret.status === 204 && ret.headers.get("redirected") === "true") throw redirect(303, ret.headers.get("location"))
    throw redirect(303, '/inventory/categories')
  },
} satisfies Actions

export const load = (async ({ params, cookies }) => {
  const ret = await securedExternalApiRequest(
    `${GATEWAY_URL}/${INVENTORY}/categories/${params.categoryId}`,
    HttpMethods.GET,
    cookies.get('Authorization'),
    cookies
  )
    if (ret.status === 204 && ret.headers.get("redirected") === "true") throw redirect(303, ret.headers.get("location"))
  return {
    category: await ret.json(),
  }
}) satisfies PageServerLoad
