import { MoneyOperationType } from '$lib/finances/types/financialTypes'
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
  isNbXnY,
} from '$lib/scripts/validator.ts'

const validateArgs = (body, category) => {
  let error = {
    failed: false,
    returnBody: {
      plannedAmount: {
        value: undefined,
        currencyCode: undefined,
      },
      category: undefined,
      plannedYear: undefined,
    },
  }

  const valueResult = validate(
    body.plannedAmount.value,
    nonEmpty,
    isNumber,
    isNonNegative
  )

  if (!valueResult.result) {
    error.failed = true
    error.returnBody.plannedAmount.value = valueResult.message
  }

  const currencyCodeResult = validate(
    body.plannedAmount.currencyCode,
    nonEmpty,
    leX(3)
  )

  if (!currencyCodeResult.result) {
    error.failed = true
    error.returnBody.plannedAmount.currencyCode = currencyCodeResult.message
  }

  const categoryResult = validate(category, nonEmpty)

  if (!categoryResult.result) {
    error.failed = true
    error.returnBody.category = categoryResult.message
  }

  const plannedYearResult = validate(
    body.plannedYear,
    nonEmpty,
    isNumber,
    isNbXnY(new Date().getFullYear(), 2100)
  )

  if (!plannedYearResult.result) {
    error.failed = true
    error.returnBody.plannedYear = plannedYearResult.message
  }

  return error
}

export const actions = {
  default: async ({ request }) => {
    const data = await request.formData()
    const category = JSON.parse(data.get('category'))
    const body = {
      plannedAmount: {
        value: data.get('plannedAmount'),
        currencyCode: data.get('currencyCode'),
      },
      operationDescription: data.get('operationDescription'),
      plannedYear: data.get('plannedYear'),
      plannedMonth: data.get('plannedMonth'),
      operationType: MoneyOperationType.EXPENSES,
    }

    const validationResult = validateArgs(body, category)

    if (validationResult.failed) {
      return fail(422, validationResult.returnBody)
    }

    body.operationCategoryId = category.id

    await unsecuredExternalApiRequest(
      FINANCES_URL + '/planned-expenses',
      HttpMethods.POST,
      body
    )
    throw redirect(303, '/finances/planned-expenses')
  },
} satisfies Actions
