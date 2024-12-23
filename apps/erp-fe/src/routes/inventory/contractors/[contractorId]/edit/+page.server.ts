import type { PageServerLoad } from './$types'
import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
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
      id: {
        val: body.id,
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
  default: async ({ cookies, request }) => {
    const data = await request.formData()
    const body = {
      id: data.get('contractorId'),
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
      INVENTORY_URL + `/contractors/${body.id}`,
      HttpMethods.PATCH,
      body
    )
    throw redirect(303, '/inventory/contractors')
  },
} satisfies Actions

export const load = (async ({ params }) => {
  const contractor = await unsecuredExternalApiRequest(
    INVENTORY_URL + `/contractors/${params.contractorId}`,
    HttpMethods.GET
  )
  return {
    contractor: await contractor.json(),
  }
}) satisfies PageServerLoad
