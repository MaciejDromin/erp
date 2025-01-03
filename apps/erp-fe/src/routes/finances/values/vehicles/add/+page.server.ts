import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests'
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
    error.returnBody.amount = valueResult.message
  }

  const currencyCodeResult = validate(body.currencyCode, nonEmpty, leX(3))

  if (!currencyCodeResult.result) {
    error.failed = true
    error.returnBody.currencyCode = currencyCodeResult.message
  }

  const objectResult = validate(object, nonEmpty)

  if (!objectResult.result) {
    error.failed = true
    error.returnBody.object = objectResult.message
  }

  return error
}

export const actions = {
  default: async ({ request }) => {
    const data = await request.formData()
    const object = JSON.parse(data.get('object'))
    const body = {
      amount: data.get('amount'),
      currencyCode: data.get('currencyCode'),
      objectType: ObjectType.VEHICLE,
    }

    const validationResult = validateArgs(body, object)

    if (validationResult.failed) {
      return fail(422, validationResult.returnBody)
    }

    body.objectId = object.id

    await unsecuredExternalApiRequest(
      `${GATEWAY_URL}/${FINANCES}/object-value`,
      HttpMethods.POST,
      body
    )
    throw redirect(303, '/finances/values/vehicles')
  },
} satisfies Actions

export const load = (async ({ params }) => {
  const objectIds = await unsecuredExternalApiRequest(
    `${GATEWAY_URL}/${FINANCES}/object-value/object-ids?objectType=${ObjectType.VEHICLE}`,
    HttpMethods.GET
  )
  return {
    objectIds: await objectIds.json(),
  }
}) satisfies PageServerLoad
