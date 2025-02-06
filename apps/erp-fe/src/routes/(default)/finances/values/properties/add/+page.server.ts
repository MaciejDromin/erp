import { securedExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { Actions } from './$types'
import type { PageServerLoad } from './$types'
import { GATEWAY_URL } from '$lib/scripts/urls'
import { FINANCES } from '$lib/scripts/serviceKey.ts'
import { ObjectType } from '$lib/finances/types/financialTypes'
import { redirect, fail } from '@sveltejs/kit'
import {
  validate,
  nonEmpty,
  isNumber,
  isNonNegative,
  leX,
} from '$lib/scripts/validator.ts'

const validateArgs = (body, object) => {
  let error = {
    failed: false,
    returnBody: {
      id: {
        val: undefined,
        message: undefined,
      },
      amount: {
        val: body.amount,
        message: undefined,
      },
      currencyCode: {
        val: body.currencyCode,
        message: undefined,
      },
      object: {
        val: object,
        message: undefined,
      },
    },
  }

  const valueResult = validate(body.amount, nonEmpty, isNumber, isNonNegative)

  if (!valueResult.result) {
    error.failed = true
    error.returnBody.amount.message = valueResult.message
  }

  const currencyCodeResult = validate(body.currencyCode, nonEmpty, leX(3))

  if (!currencyCodeResult.result) {
    error.failed = true
    error.returnBody.currencyCode.message = currencyCodeResult.message
  }

  const objectResult = validate(object, nonEmpty)

  if (!objectResult.result) {
    error.failed = true
    error.returnBody.object.message = objectResult.message
  }

  return error
}

export const actions = {
  default: async ({ request, cookies }) => {
    const data = await request.formData()
    const object = JSON.parse(data.get('object'))
    const body = {
      amount: data.get('amount'),
      currencyCode: data.get('currencyCode'),
      objectType: ObjectType.PROPERTY,
    }

    const validationResult = validateArgs(body, object)

    if (validationResult.failed) {
      return fail(422, validationResult.returnBody)
    }

    body.objectId = object.id

    const ret = await securedExternalApiRequest(
      `${GATEWAY_URL}/${FINANCES}/object-value`,
      HttpMethods.POST,
      cookies.get('Authorization'),
      cookies,
      body
    )
    if (ret.status === 204 && ret.headers.get('redirected') === 'true')
      redirect(303, ret.headers.get('location'))
    redirect(303, '/finances/values/properties')
  },
} satisfies Actions

export const load = (async ({ params, cookies }) => {
  const objectIds = await securedExternalApiRequest(
    `${GATEWAY_URL}/${FINANCES}/object-value/object-ids?objectType=${ObjectType.PROPERTY}`,
    HttpMethods.GET,
    cookies.get('Authorization'),
    cookies
  )
  if (
    objectIds.status === 204 &&
    objectIds.headers.get('redirected') === 'true'
  )
    redirect(303, objectIds.headers.get('location'))
  return {
    objectIds: await objectIds.json(),
  }
}) satisfies PageServerLoad
