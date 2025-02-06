import { securedExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { Actions } from './$types'
import { GATEWAY_URL } from '$lib/scripts/urls'
import { INVENTORY } from '$lib/scripts/serviceKey.ts'
import { redirect, fail } from '@sveltejs/kit'
import {
  validate,
  nonEmpty,
  isNumber,
  isNonNegative,
  isPositive,
  isDate,
} from '$lib/scripts/validator.ts'

const validateArgs = (body, contractor) => {
  let error = {
    failed: false,
    returnBody: {
      id: {
        val: undefined,
        message: undefined,
      },
      date: {
        val: body.date,
        message: undefined,
      },
      odometer: {
        val: body.odometer,
        message: undefined,
      },
      contractor: {
        val: contractor,
        message: undefined,
      },
      parts: {
        val: body.parts,
        message: undefined,
      },
    },
  }

  body.parts.forEach((part) => {
    error.returnBody[part.id] = {
      val: undefined,
      message: undefined,
    }
  })

  const dateResult = validate(body.date, nonEmpty, isDate)

  if (!dateResult.result) {
    error.failed = true
    error.returnBody.date.message = dateResult.message
  }

  const odometerResult = validate(
    body.odometer,
    nonEmpty,
    isNumber,
    isNonNegative
  )

  if (!odometerResult.result) {
    error.failed = true
    error.returnBody.odometer.message = odometerResult.message
  }

  const contractorResult = validate(contractor, nonEmpty)

  if (!contractorResult.result) {
    error.failed = true
    error.returnBody.contractor.message = contractorResult.message
  }

  body.parts.forEach((part) => {
    const partResult = validate(part.quantity, nonEmpty, isNumber, isPositive)

    if (!partResult.result) {
      error.failed = true
      error.returnBody[part.id].message = partResult.message
    }
  })

  return error
}

export const actions = {
  default: async ({ request, cookies }) => {
    const data = await request.formData()
    const contractor = JSON.parse(data.get('contractor'))
    const partsData = JSON.parse(data.get('partsData'))
    const body = {
      date: data.get('date'),
      odometer: data.get('odometer'),
      parts: JSON.parse(data.get('parts')),
    }

    const validationResult = validateArgs(body, contractor)

    if (validationResult.failed) {
      validationResult.returnBody.partsData = partsData
      return fail(422, validationResult.returnBody)
    }

    body.contractorId = contractor.id

    const ret = await securedExternalApiRequest(
      `${GATEWAY_URL}/${INVENTORY}/maintenance`,
      HttpMethods.POST,
      cookies.get('Authorization'),
      cookies,
      body
    )
    if (ret.status === 204 && ret.headers.get('redirected') === 'true')
      redirect(303, ret.headers.get('location'))
    redirect(303, '/inventory/maintenance')
  },
} satisfies Actions
