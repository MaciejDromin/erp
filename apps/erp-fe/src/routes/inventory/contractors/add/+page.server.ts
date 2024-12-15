import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { Actions } from './$types'
import { INVENTORY_URL } from '$lib/scripts/urls'
import { redirect, fail } from '@sveltejs/kit'
import {
  validate,
  nonEmpty,
  isNumber,
  isNbXnY,
  lbXnY,
  matchesRegex,
  emailRegex,
  websiteRegex,
} from '$lib/scripts/validator.ts'

const validateArgs = (body) => {
  let error = {
    failed: false,
    returnBody: {
      name: undefined,
      phoneNumber: undefined,
      email: undefined,
      website: undefined,
    },
  }

  const nameResult = validate(body.name, nonEmpty, lbXnY(3, 30))

  if (!nameResult.result) {
    error.failed = true
    error.returnBody.name = nameResult.message
  }

  const emailResult = validate(
    body.contactInformation.email,
    nonEmpty,
    matchesRegex(emailRegex, 'email')
  )

  if (!emailResult.result) {
    error.failed = true
    error.returnBody.email = emailResult.message
  }

  const websiteResult = validate(
    body.contactInformation.website,
    nonEmpty,
    matchesRegex(websiteRegex, 'website')
  )

  if (!websiteResult.result) {
    error.failed = true
    error.returnBody.website = websiteResult.message
  }

  const phoneNumberResult = validate(
    body.contactInformation.phoneNumber,
    nonEmpty,
    isNumber,
    isNbXnY(4, 16)
  )

  if (!phoneNumberResult.result) {
    error.failed = true
    error.returnBody.phoneNumber = phoneNumberResult.message
  }

  return error
}

export const actions = {
  default: async ({ request }) => {
    const data = await request.formData()
    const body = {
      name: data.get('name'),
      contactInformation: {
        phoneNumber: data.get('phoneNumber'),
        email: data.get('email'),
        website: data.get('website'),
      },
    }

    const validationResult = validateArgs(body)

    if (validationResult.failed) {
      return fail(422, validationResult.returnBody)
    }

    await unsecuredExternalApiRequest(
      INVENTORY_URL + '/contractors',
      HttpMethods.POST,
      body
    )
    throw redirect(303, '/inventory/contractors')
  },
} satisfies Actions
