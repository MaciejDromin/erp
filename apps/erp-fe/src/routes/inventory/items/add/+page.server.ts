import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { Actions } from './$types'
import { INVENTORY_URL } from '$lib/scripts/urls'
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
      name: undefined,
      quantity: undefined,
      category: undefined,
    },
  }

  const nameResult = validate(body.name, nonEmpty, lbXnY(3, 16))

  if (!nameResult.result) {
    error.failed = true
    error.returnBody.name = nameResult.message
  }

  const quantityResult = validate(body.quantity, nonEmpty, isNumber, isNonNegative)

  if(!quantityResult.result) {
    error.failed = true
    error.returnBody.quantity = quantityResult.message
  }

  const categoryResult = validate(category, nonEmpty)

  if (!categoryResult.result) {
    error.failed = true
    error.returnBody.category = categoryResult.message
  }

  return error
}

export const actions = {
  default: async ({ request }) => {
    const data = await request.formData()
    const category = JSON.parse(data.get('category'))
    const body = {
      name: data.get('name'),
      quantity: data.get('quantity'),
      unit: data.get('unit'),
    }

    const validationResult = validateArgs(body, category)

    if (validationResult.failed) {
      return fail(422, validationResult.returnBody)
    }

    body.categoryIds = [category.id]

    await unsecuredExternalApiRequest(
      INVENTORY_URL + '/items',
      HttpMethods.POST,
      body
    )
    throw redirect(303, '/inventory/items')
  },
} satisfies Actions
