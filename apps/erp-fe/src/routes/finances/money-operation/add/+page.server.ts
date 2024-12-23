import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { Actions } from './$types'
import { FINANCES_URL } from '$lib/scripts/urls'
import { redirect, fail } from '@sveltejs/kit'
import {
  validate,
  nonEmpty,
  isNumber,
  isNonNegative,
  leX,
} from '$lib/scripts/validator.ts'

const validateArgs = (body, category) => {
  let error = {
    failed: false,
    returnBody: {
      amount: {
        value: {
          val: body.amount.value,
          message: undefined,
        },
        currencyCode: {
          val: body.amount.currencyCode,
          message: undefined,
        },
      },
      category: {
        val: category,
        message: undefined,
      },
      operationType: {
        val: body.operationType,
        message: undefined,
      },
      operationDescription: {
        val: body.operationDescription,
        message: undefined,
      },
      id: {
        val: undefined,
        message: undefined,
      },
      effectiveDate: {
        val: undefined,
        message: undefined,
      },
    },
  }

  const valueResult = validate(
    body.amount.value,
    nonEmpty,
    isNumber,
    isNonNegative
  )

  if (!valueResult.result) {
    error.failed = true
    error.returnBody.amount.value.message = valueResult.message
  }

  const currencyCodeResult = validate(
    body.amount.currencyCode,
    nonEmpty,
    leX(3)
  )

  if (!currencyCodeResult.result) {
    error.failed = true
    error.returnBody.amount.currencyCode.message = currencyCodeResult.message
  }

  const categoryResult = validate(category, nonEmpty)

  if (!categoryResult.result) {
    error.failed = true
    error.returnBody.category.message = categoryResult.message
  }

  return error
}

export const actions = {
  default: async ({ request }) => {
    const data = await request.formData()
    const category = JSON.parse(data.get('category'))
    const body = {
      amount: {
        value: data.get('amount'),
        currencyCode: data.get('currencyCode'),
      },
      operationType: data.get('operationType'),
      operationDescription: data.get('operationDescription'),
    }

    const validationResult = validateArgs(body, category)

    if (validationResult.failed) {
      return fail(422, validationResult.returnBody)
    }

    body.operationCategoryId = category.id

    await unsecuredExternalApiRequest(
      FINANCES_URL + '/money-operation',
      HttpMethods.POST,
      body
    )
    throw redirect(303, '/finances/money-operation')
  },
} satisfies Actions
