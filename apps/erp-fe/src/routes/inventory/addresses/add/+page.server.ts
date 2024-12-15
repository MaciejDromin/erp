import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { Actions } from './$types'
import { INVENTORY_URL } from '$lib/scripts/urls'
import { redirect, fail } from '@sveltejs/kit'
import { validate, nonEmpty, lbXnY } from '$lib/scripts/validator.ts'

const validateArgs = (body) => {
  let error = {
    failed: false,
    returnBody: {
      addressLine: undefined,
      city: undefined,
      country: undefined,
      province: undefined,
      postalCode: undefined,
    },
  }

  const addressResult = validate(body.addressLine, nonEmpty, lbXnY(3, 50))

  if (!addressResult.result) {
    error.failed = true
    error.returnBody.addressLine = addressResult.message
  }

  const cityResult = validate(body.city, nonEmpty, lbXnY(3, 30))

  if (!cityResult.result) {
    error.failed = true
    error.returnBody.city = cityResult.message
  }

  const countryResult = validate(body.country, nonEmpty, lbXnY(3, 30))

  if (!countryResult.result) {
    error.failed = true
    error.returnBody.country = countryResult.message
  }

  const provinceResult = validate(body.province, nonEmpty, lbXnY(3, 50))

  if (!provinceResult.result) {
    error.failed = true
    error.returnBody.province = provinceResult.message
  }

  const postalCodeResult = validate(body.postalCode, nonEmpty, lbXnY(3, 10))

  if (!postalCodeResult.result) {
    error.failed = true
    error.returnBody.postalCode = postalCodeResult.message
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
      INVENTORY_URL + '/addresses',
      HttpMethods.POST,
      body
    )
    throw redirect(303, '/inventory/addresses')
  },
} satisfies Actions
