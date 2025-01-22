import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests'
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
  isDate,
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
        val: body.id,
        message: undefined,
      },
      effectiveDate: {
        val: body.effectiveDate,
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

  const dateResult = validate(body.effectiveDate, nonEmpty, isDate)

  if (!dateResult.result) {
    error.failed = true
    error.returnBody.effectiveDate.message = dateResult.message
  }

  return error
}

export const actions = {
  default: async ({ request }) => {
    const data = await request.formData()
    const category = JSON.parse(data.get('category'))
    const body = {
      id: data.get('operationId'),
      amount: {
        value: data.get('amount'),
        currencyCode: data.get('currencyCode'),
      },
      operationType: data.get('operationType'),
      operationDescription: data.get('operationDescription'),
      effectiveDate: data.get('effectiveDate'),
    }

    const validationResult = validateArgs(body, category)

    if (validationResult.failed) {
      return fail(422, validationResult.returnBody)
    }

    body.operationCategory = { id: category.id }

    await unsecuredExternalApiRequest(
      `${GATEWAY_URL}/${FINANCES}/money-operation/${body.id}`,
      HttpMethods.PATCH,
      body
    )
    throw redirect(303, '/finances/money-operation')
  },
} satisfies Actions

export const load = (async ({ params }) => {
  const operation = await unsecuredExternalApiRequest(
    `${GATEWAY_URL}/${FINANCES}/money-operation/${params.operationId}`,
    HttpMethods.GET
  )
  return {
    operation: await operation.json(),
  }
}) satisfies PageServerLoad
