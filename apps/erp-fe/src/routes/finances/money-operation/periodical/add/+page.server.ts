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
  le3,
  isNb1N12,
} from '$lib/scripts/validator.ts'

const validateArgs = (body, category) => {
  let error = {
    failed: false,
    returnBody: {
      amount: {
        value: undefined,
        currencyCode: undefined,
      },
      category: undefined,
      repetitionPeriod: undefined,
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
    error.returnBody.amount.value = valueResult.message
  }

  const currencyCodeResult = validate(body.amount.currencyCode, nonEmpty, le3)

  if (!currencyCodeResult.result) {
    error.failed = true
    error.returnBody.amount.currencyCode = currencyCodeResult.message
  }

  const categoryResult = validate(category, nonEmpty)

  if (!categoryResult.result) {
    error.failed = true
    error.returnBody.category = categoryResult.message
  }

  const repetitionResult = validate(
    body.repetitionPeriod,
    nonEmpty,
    isNumber,
    isNb1N12
  )

  if (!repetitionResult.result) {
    error.failed = true
    error.returnBody.repetitionPeriod = repetitionResult.message
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
      nextApplicableMonth: data.get('nextApplicableMonth'),
      repetitionPeriod: data.get('repetitionPeriod'),
    }

    const validationResult = validateArgs(body, category)

    if (validationResult.failed) {
      return fail(422, validationResult.returnBody)
    }

    body.operationCategoryId = category.id

    await unsecuredExternalApiRequest(
      FINANCES_URL + '/money-operation/periodical',
      HttpMethods.POST,
      body
    )
    throw redirect(303, '/finances/money-operation/periodical')
  },
} satisfies Actions
