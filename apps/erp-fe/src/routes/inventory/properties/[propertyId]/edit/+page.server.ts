import type { PageServerLoad } from './$types'
import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import { GATEWAY_URL } from '$lib/scripts/urls'
import { INVENTORY } from '$lib/scripts/serviceKey.ts'
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
      id: {
        val: undefined,
        message: undefined,
      },
      name: {
        val: body.name,
        message: undefined,
      },
      uniqueIdentifier: {
        val: body.uniqueIdentifier,
        message: undefined,
      },
      landRegister: {
        val: body.landRegister,
        message: undefined,
      },
      address: {
        val: address,
        message: undefined,
      },
      propertyInformation: {
        propertyType: {
          val: body.propertyInformation.propertyType,
          message: undefined,
        },
        landClassification: {
          val: body.propertyInformation.landClassification,
          message: undefined,
        },
        landArea: {
          area: {
            val: body.propertyInformation.landArea.area,
            message: undefined,
          },
          unit: {
            val: body.propertyInformation.landArea.unit,
            message: undefined,
          },
        },
      },
    },
  }

  const addressResult = validate(address, nonEmpty)

  if (!addressResult.result) {
    error.failed = true
    error.returnBody.address.message = addressResult.message
  }

  const nameResult = validate(body.name, nonEmpty, lbXnY(3, 30))

  if (!nameResult.result) {
    error.failed = true
    error.returnBody.name.message = nameResult.message
  }

  const uniqueIdentifierResult = validate(
    body.uniqueIdentifier,
    nonEmpty,
    lbXnY(3, 30)
  )

  if (!uniqueIdentifierResult.result) {
    error.failed = true
    error.returnBody.uniqueIdentifier.message = uniqueIdentifierResult.message
  }

  const landRegisterResult = validate(body.landRegister, nonEmpty, lbXnY(3, 50))

  if (!landRegisterResult.result) {
    error.failed = true
    error.returnBody.landRegister.message = landRegisterResult.message
  }

  const areaResult = validate(
    body.propertyInformation.landArea.area,
    nonEmpty,
    isNumber,
    isNonNegative
  )

  if (!areaResult.result) {
    error.failed = true
    error.returnBody.propertyInformation.landArea.area.message =
      areaResult.message
  }

  return error
}

export const actions = {
  default: async ({ cookies, request }) => {
    const data = await request.formData()
    const address = JSON.parse(data.get('address'))
    const body = {
      id: data.get('propertyId'),
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
      `${GATEWAY_URL}/${INVENTORY}/properties/${body.id}`,
      HttpMethods.PATCH,
      body
    )
    throw redirect(303, '/inventory/properties')
  },
} satisfies Actions

export const load = (async ({ params }) => {
  const property = await unsecuredExternalApiRequest(
    `${GATEWAY_URL}/${INVENTORY}/properties/${params.propertyId}`,
    HttpMethods.GET
  )
  return {
    property: await property.json(),
  }
}) satisfies PageServerLoad
