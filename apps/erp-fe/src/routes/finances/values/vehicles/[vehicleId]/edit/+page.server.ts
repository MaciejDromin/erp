import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { Actions } from './$types'
import type { PageServerLoad } from './$types'
import { FINANCES_URL } from '$lib/scripts/urls'
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
        val: body.id,
        message: undefined,
      },
      amount: {
        val: body.amount.value,
        message: undefined,
      },
      currencyCode: {
        val: body.amount.currencyCode,
        message: undefined,
      },
      object: {
        val: object,
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
    error.returnBody.amount.message = valueResult.message
  }

  const currencyCodeResult = validate(
    body.amount.currencyCode,
    nonEmpty,
    leX(3)
  )

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
  default: async ({ request }) => {
    const data = await request.formData()
    const object = JSON.parse(data.get('object'))
    const body = {
      id: data.get('objectValueId'),
      amount: {
        value: data.get('amount'),
        currencyCode: data.get('currencyCode'),
      },
    }

    const validationResult = validateArgs(body, object)

    if (validationResult.failed) {
      return fail(422, validationResult.returnBody)
    }

    body.objectId = object.id

    await unsecuredExternalApiRequest(
      FINANCES_URL + `/object-value/${body.id}`,
      HttpMethods.PATCH,
      body
    )
    throw redirect(303, '/finances/values/vehicles')
  },
} satisfies Actions

export const load = (async ({ params }) => {
  const objectIds = await unsecuredExternalApiRequest(
    FINANCES_URL + `/object-value/object-ids?objectType=${ObjectType.VEHICLE}`,
    HttpMethods.GET
  )
  const ov = await unsecuredExternalApiRequest(
    FINANCES_URL + `/object-value/${params.vehicleId}`,
    HttpMethods.GET
  )
  return {
    objectIds: await objectIds.json(),
    objectValue: await ov.json(),
  }
}) satisfies PageServerLoad
