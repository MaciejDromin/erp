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
      id: {
        val: undefined,
        message: undefined,
      },
      name: {
        val: body.name,
        message: undefined,
      },
      phoneNumber: {
        val: body.contactInformation.phoneNumber,
        message: undefined,
      },
      email: {
        val: body.contactInformation.email,
        message: undefined,
      },
      website: {
        val: body.contactInformation.website,
        message: undefined,
      },
    },
  }

  const nameResult = validate(body.name, nonEmpty, lbXnY(3, 30))

  if (!nameResult.result) {
    error.failed = true
    error.returnBody.name.message = nameResult.message
  }

  const emailResult = validate(
    body.contactInformation.email,
    nonEmpty,
    matchesRegex(emailRegex, 'email')
  )

  if (!emailResult.result) {
    error.failed = true
    error.returnBody.email.message = emailResult.message
  }

  const websiteResult = validate(
    body.contactInformation.website,
    nonEmpty,
    matchesRegex(websiteRegex, 'website')
  )

  if (!websiteResult.result) {
    error.failed = true
    error.returnBody.website.message = websiteResult.message
  }

  const phoneNumberResult = validate(
    body.contactInformation.phoneNumber,
    nonEmpty,
    isNumber,
    isNbXnY(4, 16)
  )

  if (!phoneNumberResult.result) {
    error.failed = true
    error.returnBody.phoneNumber.message = phoneNumberResult.message
  }

  return error
}

export const actions = {
  default: async ({ request, cookies }) => {
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

    const ret = await securedExternalApiRequest(
      `${GATEWAY_URL}/${INVENTORY}/contractors`,
      HttpMethods.POST,
      cookies.get('Authorization'),
      cookies,
      body
    )
    if (ret.status === 204 && ret.headers.get('redirected') === 'true')
      redirect(303, ret.headers.get('location'));
    redirect(303, '/inventory/contractors');
  },
} satisfies Actions
