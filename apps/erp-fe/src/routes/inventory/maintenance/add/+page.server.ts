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
  isPositive,
  isDate,
} from '$lib/scripts/validator.ts'

const validateArgs = (body, contractor) => {
  let error = {
    failed: false,
    returnBody: {
      date: undefined,
      odometer: undefined,
      contractor: undefined,
    },
  }

  body.parts.forEach((part) => {
    error.returnBody[part.id] = undefined
  })

  const dateResult = validate(body.date, nonEmpty, isDate)

  if (!dateResult.result) {
    error.failed = true
    error.returnBody.date = dateResult.message
  }

  const odometerResult = validate(
    body.odometer,
    nonEmpty,
    isNumber,
    isNonNegative
  )

  if (!odometerResult.result) {
    error.failed = true
    error.returnBody.odometer = odometerResult.message
  }

  const contractorResult = validate(contractor, nonEmpty)

  if (!contractorResult.result) {
    error.failed = true
    error.returnBody.contractor = contractorResult.message
  }

  body.parts.forEach((part) => {
    const partResult = validate(part.quantity, nonEmpty, isNumber, isPositive)

    if (!partResult.result) {
      error.failed = true
      error.returnBody[part.id] = partResult.message
    }
  })

  return error
}

export const actions = {
  default: async ({ request }) => {
    const data = await request.formData()
    const contractor = JSON.parse(data.get('contractor'))
    const body = {
      date: data.get('date'),
      odometer: data.get('odometer'),
      parts: JSON.parse(data.get('parts')),
    }

    const validationResult = validateArgs(body, contractor)

    if (validationResult.failed) {
      return fail(422, validationResult.returnBody)
    }

    body.contractorId = contractor.id

    await unsecuredExternalApiRequest(
      INVENTORY_URL + '/maintenance',
      HttpMethods.POST,
      body
    )
    throw redirect(303, '/inventory/maintenance')
  },
} satisfies Actions
