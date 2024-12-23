import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { Actions } from './$types'
import { INVENTORY_URL } from '$lib/scripts/urls'
import { redirect, fail } from '@sveltejs/kit'
import { validate, nonEmpty, lbXnY } from '$lib/scripts/validator.ts'

const validateArgs = (body, manufacturer) => {
  let error = {
    failed: false,
    returnBody: {
      id: {
        val: undefined,
        message: undefined,
      },
      name: {
        val: body.name,
        message: undefined,
      },
      partNumber: {
        val: body.partNumber,
        message: undefined,
      },
      manufacturer: {
        val: manufacturer,
        message: undefined,
      },
    },
  }

  const nameResult = validate(body.name, nonEmpty, lbXnY(3, 30))

  if (!nameResult.result) {
    error.failed = true
    error.returnBody.name.message = nameResult.message
  }

  const partNumberResult = validate(body.partNumber, nonEmpty, lbXnY(3, 30))

  if (!partNumberResult.result) {
    error.failed = true
    error.returnBody.partNumber.message = partNumberResult.message
  }

  const manufacturerResult = validate(manufacturer, nonEmpty)

  if (!manufacturerResult.result) {
    error.failed = true
    error.returnBody.manufacturer.message = manufacturerResult.message
  }

  return error
}

export const actions = {
  default: async ({ request }) => {
    const data = await request.formData()
    const manufacturer = JSON.parse(data.get('manufacturer'))
    const body = {
      name: data.get('name'),
      partNumber: data.get('partNumber'),
    }

    const validationResult = validateArgs(body, manufacturer)

    if (validationResult.failed) {
      return fail(422, validationResult.returnBody)
    }

    body.manufacturerId = manufacturer.id

    await unsecuredExternalApiRequest(
      INVENTORY_URL + '/parts',
      HttpMethods.POST,
      body
    )
    throw redirect(303, '/inventory/parts')
  },
} satisfies Actions
