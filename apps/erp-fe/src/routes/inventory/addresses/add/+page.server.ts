import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { Actions } from './$types'
import { GATEWAY_URL } from '$lib/scripts/urls'
import { INVENTORY } from '$lib/scripts/serviceKey.ts'
import { redirect, fail } from '@sveltejs/kit'
import { validate, nonEmpty, lbXnY } from '$lib/scripts/validator.ts'

const validateArgs = (body) => {
  let error = {
    failed: false,
    returnBody: {
      id: {
        val: undefined,
        message: undefined,
      },
      addressLine: {
        val: body.addressLine,
        message: undefined,
      },
      city: {
        val: body.city,
        message: undefined,
      },
      country: {
        val: body.country,
        message: undefined,
      },
      province: {
        val: body.province,
        message: undefined,
      },
      postalCode: {
        val: body.postalCode,
        message: undefined,
      },
    },
  }

  const addressResult = validate(body.addressLine, nonEmpty, lbXnY(3, 50))

  if (!addressResult.result) {
    error.failed = true
    error.returnBody.addressLine.message = addressResult.message
  }

  const cityResult = validate(body.city, nonEmpty, lbXnY(3, 30))

  if (!cityResult.result) {
    error.failed = true
    error.returnBody.city.message = cityResult.message
  }

  const countryResult = validate(body.country, nonEmpty, lbXnY(3, 30))

  if (!countryResult.result) {
    error.failed = true
    error.returnBody.country.message = countryResult.message
  }

  const provinceResult = validate(body.province, nonEmpty, lbXnY(3, 50))

  if (!provinceResult.result) {
    error.failed = true
    error.returnBody.province.message = provinceResult.message
  }

  const postalCodeResult = validate(body.postalCode, nonEmpty, lbXnY(3, 10))

  if (!postalCodeResult.result) {
    error.failed = true
    error.returnBody.postalCode.message = postalCodeResult.message
  }

  return error
}

export const actions = {
  default: async ({ cookies, request }) => {
    const data = await request.formData()
    const body = {
      addressLine: data.get('addressLine'),
      city: data.get('city'),
      country: data.get('country'),
      province: data.get('province'),
      postalCode: data.get('postalCode'),
    }

    const validationResult = validateArgs(body)

    if (validationResult.failed) {
      return fail(422, validationResult.returnBody)
    }

    await unsecuredExternalApiRequest(
      `${GATEWAY_URL}/${INVENTORY}/addresses`,
      HttpMethods.POST,
      body
    )
    throw redirect(303, '/inventory/addresses')
  },
} satisfies Actions
