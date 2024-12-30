import type { PageServerLoad } from './$types'
import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import { GATEWAY_URL } from '$lib/scripts/urls'
import { INVENTORY } from '$lib/scripts/serviceKey.ts'
import { redirect, fail } from '@sveltejs/kit'
import {
  validate,
  nonEmpty,
  isNumber,
  isNonNegative,
  isNbXnY,
  lbXnY,
} from '$lib/scripts/validator.ts'

const validateArgs = (body, category) => {
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
      categories: {
        val: [category],
        message: undefined,
      },
      quantity: {
        val: body.quantity,
        message: undefined,
      },
      unit: {
        val: body.unit,
        message: undefined,
      },
    },
  }

  const nameResult = validate(body.name, nonEmpty, lbXnY(3, 16))

  if (!nameResult.result) {
    error.failed = true
    error.returnBody.name.message = nameResult.message
  }

  const quantityResult = validate(
    body.quantity,
    nonEmpty,
    isNumber,
    isNonNegative
  )

  if (!quantityResult.result) {
    error.failed = true
    error.returnBody.quantity.message = quantityResult.message
  }

  const categoryResult = validate(category, nonEmpty)

  if (!categoryResult.result) {
    error.failed = true
    error.returnBody.category.message = categoryResult.message
  }

  return error
}

export const actions = {
  default: async ({ cookies, request }) => {
    const data = await request.formData()
    const category = JSON.parse(data.get('category'))
    const body = {
      id: data.get('itemId'),
      name: data.get('name'),
      quantity: Number(data.get('quantity')),
      unit: data.get('unit'),
    }

    const validationResult = validateArgs(body, category)

    if (validationResult.failed) {
      return fail(422, validationResult.returnBody)
    }

    body.categories = [category.id]

    await unsecuredExternalApiRequest(
      `${GATEWAY_URL}/${INVENTORY}/items/${body.id}`,
      HttpMethods.PATCH,
      body
    )
    throw redirect(303, '/inventory/items')
  },
} satisfies Actions

export const load = (async ({ params }) => {
  const item = await unsecuredExternalApiRequest(
    `${GATEWAY_URL}/${INVENTORY}/items/${params.itemId}`,
    HttpMethods.GET
  )
  return {
    item: await item.json(),
  }
}) satisfies PageServerLoad
