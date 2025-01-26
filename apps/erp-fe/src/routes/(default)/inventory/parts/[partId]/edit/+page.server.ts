import type { PageServerLoad } from './$types'
import { securedExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import { GATEWAY_URL } from '$lib/scripts/urls'
import { INVENTORY } from '$lib/scripts/serviceKey.ts'
import { redirect, fail } from '@sveltejs/kit'
import { validate, nonEmpty, lbXnY } from '$lib/scripts/validator.ts'

const validateArgs = (body, manufacturer) => {
  let error = {
    failed: false,
    returnBody: {
      id: {
        val: body.id,
        message: undefined,
      },
      name: {
        val: body.name,
        message: undefined,
      },
      partNumber: {
        val: body.partNumber,
        message: undefined,
      },
      manufacturer: {
        val: manufacturer,
        message: undefined,
      },
    },
  }

  const nameResult = validate(body.name, nonEmpty, lbXnY(3, 30))

  if (!nameResult.result) {
    error.failed = true
    error.returnBody.name.message = nameResult.message
  }

  const partNumberResult = validate(body.partNumber, nonEmpty, lbXnY(3, 30))

  if (!partNumberResult.result) {
    error.failed = true
    error.returnBody.partNumber.message = partNumberResult.message
  }

  const manufacturerResult = validate(manufacturer, nonEmpty)

  if (!manufacturerResult.result) {
    error.failed = true
    error.returnBody.manufacturer.message = manufacturerResult.message
  }

  return error
}

export const actions = {
  default: async ({ cookies, request }) => {
    const data = await request.formData()
    const manufacturer = JSON.parse(data.get('manufacturer'))
    const body = {
      id: data.get('partId'),
      name: data.get('name'),
      partNumber: data.get('partNumber'),
    }

    const validationResult = validateArgs(body, manufacturer)

    if (validationResult.failed) {
      return fail(422, validationResult.returnBody)
    }

    body.manufacturerId = manufacturer.id

    const ret = await securedExternalApiRequest(
      `${GATEWAY_URL}/${INVENTORY}/parts/${body.id}`,
      HttpMethods.PATCH,
      cookies.get('Authorization'),
      cookies,
      body
    )
    if (ret.status === 204 && ret.headers.get("redirected") === "true") throw redirect(303, ret.headers.get("location"))
    throw redirect(303, '/inventory/parts')
  },
} satisfies Actions

export const load = (async ({ params, cookies }) => {
  const ret = await securedExternalApiRequest(
    `${GATEWAY_URL}/${INVENTORY}/parts/${params.partId}`,
    HttpMethods.GET,
    cookies.get('Authorization'),
    cookies,
  )
  if (ret.status === 204 && ret.headers.get("redirected") === "true") throw redirect(303, ret.headers.get("location"))
  return {
    part: await ret.json(),
  }
}) satisfies PageServerLoad
