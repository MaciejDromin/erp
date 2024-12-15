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
  isNbXnY,
  leX,
  lbXnY,
} from '$lib/scripts/validator.ts'

const validateArgs = (body) => {
  let error = {
    failed: false,
    returnBody: {
      name: undefined,
      year: undefined,
      odometer: undefined,
      model: undefined,
      engineType: undefined,
      vin: undefined,
      registrationPlate: undefined,
    },
  }

  const nameResult = validate(body.name, nonEmpty, lbXnY(3, 30))

  if (!nameResult.result) {
    error.failed = true
    error.returnBody.name = nameResult.message
  }

  const modelResult = validate(body.model, nonEmpty, lbXnY(3, 30))

  if (!modelResult.result) {
    error.failed = true
    error.returnBody.model = modelResult.message
  }

  const engineTypeResult = validate(body.engineType, nonEmpty, lbXnY(3, 30))

  if (!engineTypeResult.result) {
    error.failed = true
    error.returnBody.engineType = engineTypeResult.message
  }

  const vinResult = validate(body.vin, nonEmpty, leX(17))

  if (!vinResult.result) {
    error.failed = true
    error.returnBody.vin = vinResult.message
  }

  const registrationPlateResult = validate(
    body.registrationPlate,
    nonEmpty,
    lbXnY(3, 30)
  )

  if (!registrationPlateResult.result) {
    error.failed = true
    error.returnBody.registrationPlate = registrationPlateResult.message
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

  const yearResult = validate(
    body.year,
    nonEmpty,
    isNumber,
    isNbXnY(1900, new Date().getFullYear())
  )

  if (!yearResult.result) {
    error.failed = true
    error.returnBody.year = yearResult.message
  }

  return error
}

export const actions = {
  default: async ({ request }) => {
    const data = await request.formData()
    const body = {
      name: data.get('name'),
      year: data.get('year'),
      odometer: data.get('odometer'),
      bodyStyle: data.get('bodyStyle'),
      make: data.get('make'),
      model: data.get('model'),
      fuelType: data.get('fuelType'),
      driveTrain: data.get('driveTrain'),
      transmission: data.get('transmission'),
      engineType: data.get('engineType'),
      vin: data.get('vin'),
      registrationPlate: data.get('registrationPlate'),
    }

    const validationResult = validateArgs(body)

    if (validationResult.failed) {
      return fail(422, validationResult.returnBody)
    }

    await unsecuredExternalApiRequest(
      INVENTORY_URL + '/vehicles',
      HttpMethods.POST,
      body
    )
    throw redirect(303, '/inventory/vehicles')
  },
} satisfies Actions
