import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { Actions } from './$types'
import { AUTH_URL } from '$lib/scripts/urls'
import { redirect, fail } from '@sveltejs/kit'
import {
  validate,
  nonEmpty,
} from '$lib/scripts/validator.ts'

const validateArgs = (body) => {
  let error = {
    failed: false,
    returnBody: {
      email: {
        val: body.email,
        message: undefined,
      },
      password: {
        val: "",
        message: undefined,
      },
    },
  }

  const emailResult = validate(
    body.email,
    nonEmpty
  )

  if (!emailResult.result) {
    error.failed = true
    error.returnBody.email.message = emailResult.message
  }

  const passwordResult = validate(
    body.password,
    nonEmpty
  )

  if (!passwordResult.result) {
    error.failed = true
    error.returnBody.password.message = passwordResult.message
  }

  return error
}

export const actions = {
  default: async ({ cookies, request }) => {
    const data = await request.formData()
    const body = {
      email: data.get('email'),
      password: data.get('password'),
    }

    const validationResult = validateArgs(body)

    if (validationResult.failed) {
      return fail(422, validationResult.returnBody)
    }

    const resp = await unsecuredExternalApiRequest(
      `${AUTH_URL}/login`,
      HttpMethods.POST,
      body
    )
    const opts = {
      path: '/'
    }
    const respBody = await resp.json()
    cookies.set('Authorization', `Bearer ${respBody.authToken}`, opts)
    cookies.set('Refresh-Token', respBody.refreshToken, opts)
    cookies.set('X-OrgId', respBody.orgId, opts)
    throw redirect(303, '/')
  },
} satisfies Actions
