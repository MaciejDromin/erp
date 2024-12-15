import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { Actions } from './$types'
import { INVENTORY_URL } from '$lib/scripts/urls'
import { redirect, fail } from '@sveltejs/kit'
import {
  validate,
  nonEmpty,
  lbXnY,
  isNumber,
  isNonNegative,
} from '$lib/scripts/validator.ts'

const validateArgs = (body, address) => {
  let error = {
    failed: false,
    returnBody: {
      name: undefined,
      uniqueIdentifier: undefined,
      landRegister: undefined,
      area: undefined,
    },
  }

  const addressResult = validate(body.address, nonEmpty)

  if (!addressResult.result) {
    error.failed = true
    error.returnBody.address = addressResult.message
  }

  const nameResult = validate(body.name, nonEmpty, lbXnY(3, 30))

  if (!nameResult.result) {
    error.failed = true
    error.returnBody.name = nameResult.message
  }

  const uniqueIdentifierResult = validate(
    body.uniqueIdentifier,
    nonEmpty,
    lbXnY(3, 30)
  )

  if (!uniqueIdentifierResult.result) {
    error.failed = true
    error.returnBody.uniqueIdentifier = uniqueIdentifierResult.message
  }

  const landRegisterResult = validate(body.landRegister, nonEmpty, lbXnY(3, 50))

  if (!landRegisterResult.result) {
    error.failed = true
    error.returnBody.landRegister = landRegisterResult.message
  }

  const areaResult = validate(
    body.propertyInformation.landArea.area,
    nonEmpty,
    isNumber,
    isNonNegative
  )

  if (!areaResult.result) {
    error.failed = true
    error.returnBody.area = areaResult.message
  }

  return error
}

export const actions = {
  default: async ({ request }) => {
    const data = await request.formData()
    const address = JSON.parse(data.get('address'))
    const body = {
      name: data.get('name'),
      uniqueIdentifier: data.get('uniqueIdentifier'),
      landRegister: data.get('landRegister'),
      propertyInformation: {
        propertyType: data.get('propertyType'),
        landClassification: data.get('landClassification'),
        landArea: {
          area: Number(data.get('area')),
          unit: data.get('areaUnit'),
        },
      },
    }

    const validationResult = validateArgs(body, address)

    if (validationResult.failed) {
      return fail(422, validationResult.returnBody)
    }

    body.addressId = address.id

    await unsecuredExternalApiRequest(
      INVENTORY_URL + '/properties',
      HttpMethods.POST,
      body
    )
    throw redirect(303, '/inventory/properties')
  },
} satisfies Actions
