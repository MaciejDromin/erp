import type { PageServerLoad } from './$types'
import { securedExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import { GATEWAY_URL } from '$lib/scripts/urls'
import { INVENTORY } from '$lib/scripts/serviceKey.ts'
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
      id: {
        val: body.id,
        message: undefined,
      },
      name: {
        val: body.name,
        message: undefined,
      },
      year: {
        val: body.year,
        message: undefined,
      },
      odometer: {
        val: body.odometer,
        message: undefined,
      },
      bodyStyle: {
        val: body.bodyStyle,
        message: undefined,
      },
      make: {
        val: body.make,
        message: undefined,
      },
      model: {
        val: body.model,
        message: undefined,
      },
      fuelType: {
        val: body.fuelType,
        message: undefined,
      },
      driveTrain: {
        val: body.driveTrain,
        message: undefined,
      },
      transmission: {
        val: body.transmission,
        message: undefined,
      },
      engineType: {
        val: body.engineType,
        message: undefined,
      },
      vin: {
        val: body.vin,
        message: undefined,
      },
      registrationPlate: {
        val: body.registrationPlate,
        message: undefined,
      },
    },
  }

  const nameResult = validate(body.name, nonEmpty, lbXnY(3, 30))

  if (!nameResult.result) {
    error.failed = true
    error.returnBody.name.message = nameResult.message
  }

  const modelResult = validate(body.model, nonEmpty, lbXnY(3, 30))

  if (!modelResult.result) {
    error.failed = true
    error.returnBody.model.message = modelResult.message
  }

  const engineTypeResult = validate(body.engineType, nonEmpty, lbXnY(3, 30))

  if (!engineTypeResult.result) {
    error.failed = true
    error.returnBody.engineType.message = engineTypeResult.message
  }

  const vinResult = validate(body.vin, nonEmpty, leX(17))

  if (!vinResult.result) {
    error.failed = true
    error.returnBody.vin.message = vinResult.message
  }

  const registrationPlateResult = validate(
    body.registrationPlate,
    nonEmpty,
    lbXnY(3, 30)
  )

  if (!registrationPlateResult.result) {
    error.failed = true
    error.returnBody.registrationPlate.message = registrationPlateResult.message
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

  const yearResult = validate(
    body.year,
    nonEmpty,
    isNumber,
    isNbXnY(1900, new Date().getFullYear())
  )

  if (!yearResult.result) {
    error.failed = true
    error.returnBody.year.message = yearResult.message
  }

  return error
}

export const actions = {
  default: async ({ cookies, request }) => {
    const data = await request.formData()
    const body = {
      id: data.get('vehicleId'),
      name: data.get('name'),
      year: Number(data.get('year')),
      odometer: Number(data.get('odometer')),
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

    const ret = await securedExternalApiRequest(
      `${GATEWAY_URL}/${INVENTORY}/vehicles/${body.id}`,
      HttpMethods.PATCH,
      cookies.get('Authorization'),
      cookies,
      body
    )
    if (ret.status === 204 && ret.headers.get("redirected") === "true") throw redirect(303, ret.headers.get("location"))
    throw redirect(303, '/inventory/vehicles')
  },
} satisfies Actions

export const load = (async ({ params, cookies }) => {
  const ret = await securedExternalApiRequest(
    `${GATEWAY_URL}/${INVENTORY}/vehicles/${params.vehicleId}`,
    HttpMethods.GET,
    cookies.get('Authorization'),
    cookies,
  )
  if (ret.status === 204 && ret.headers.get("redirected") === "true") throw redirect(303, ret.headers.get("location"))
  return {
    vehicle: await ret.json(),
  }
}) satisfies PageServerLoad
