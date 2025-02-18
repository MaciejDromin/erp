import { securedExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { Actions } from './$types'
import type { PageServerLoad } from './$types'
import { GATEWAY_URL } from '$lib/scripts/urls'
import { FINANCES } from '$lib/scripts/serviceKey.ts'
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
      id: {
        val: body.id,
        message: undefined,
      },
      plannedAmount: {
        value: {
          val: body.plannedAmount.value,
          message: undefined,
        },
        currencyCode: {
          val: body.plannedAmount.currencyCode,
          message: undefined,
        },
      },
      category: {
        val: category,
        message: undefined,
      },
      plannedYear: {
        val: body.plannedYear,
        message: undefined,
      },
      plannedMonth: {
        val: body.plannedMonth,
        message: undefined,
      },
      operationDescription: {
        val: body.operationDescription,
        message: undefined,
      },
      operationType: {
        val: body.operationType,
        message: undefined,
      },
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
    error.returnBody.plannedAmount.value.message = valueResult.message
  }

  const currencyCodeResult = validate(
    body.plannedAmount.currencyCode,
    nonEmpty,
    leX(3)
  )

  if (!currencyCodeResult.result) {
    error.failed = true
    error.returnBody.plannedAmount.currencyCode.message =
      currencyCodeResult.message
  }

  const categoryResult = validate(category, nonEmpty)

  if (!categoryResult.result) {
    error.failed = true
    error.returnBody.category.message = categoryResult.message
  }

  const plannedYearResult = validate(
    body.plannedYear,
    nonEmpty,
    isNumber,
    isNbXnY(new Date().getFullYear(), 2100)
  )

  if (!plannedYearResult.result) {
    error.failed = true
    error.returnBody.plannedYear.message = plannedYearResult.message
  }

  return error
}

export const actions = {
  default: async ({ request, cookies }) => {
    const data = await request.formData()
    const category = JSON.parse(data.get('category'))
    const body = {
      id: data.get('plannedExpenseId'),
      plannedAmount: {
        value: data.get('plannedAmount'),
        currencyCode: data.get('currencyCode'),
      },
      operationDescription: data.get('operationDescription'),
      plannedYear: Number(data.get('plannedYear')),
      plannedMonth: data.get('plannedMonth'),
    }

    const validationResult = validateArgs(body, category)

    if (validationResult.failed) {
      return fail(422, validationResult.returnBody)
    }

    body.operationCategoryId = category.id

    const ret = await securedExternalApiRequest(
      `${GATEWAY_URL}/${FINANCES}/planned-expenses/${body.id}`,
      HttpMethods.PATCH,
      cookies.get('Authorization'),
      cookies,
      body
    )
    if (ret.status === 204 && ret.headers.get('redirected') === 'true')
      redirect(303, ret.headers.get('location'))
    redirect(303, '/finances/planned-expenses')
  },
} satisfies Actions

export const load = (async ({ params, cookies }) => {
  const ret = await securedExternalApiRequest(
    `${GATEWAY_URL}/${FINANCES}/planned-expenses/${params.plannedExpenseId}`,
    HttpMethods.GET,
    cookies.get('Authorization'),
    cookies
  )
  if (ret.status === 204 && ret.headers.get('redirected') === 'true')
    redirect(303, ret.headers.get('location'))
  return {
    plannedExpense: await ret.json(),
  }
}) satisfies PageServerLoad
