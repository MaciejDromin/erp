import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import type { Actions } from './$types'
import type { PageServerLoad } from './$types'
import { FINANCES_URL } from '$lib/scripts/urls'
import { redirect } from '@sveltejs/kit'

export const actions = {
  default: async ({ request }) => {
    const data = await request.formData()
    const category = JSON.parse(data.get('category'))
    const body = {
      uuid: data.get('operationId'),
      amount: {
        value: data.get('amount'),
        currencyCode: data.get('currencyCode'),
      },
      operationType: data.get('operationType'),
      operationDescription: data.get('operationDescription'),
      operationCategoryId: category.uuid,
    }
    await unsecuredExternalApiRequest(
      FINANCES_URL + `/finances/money-operation/${body.uuid}`,
      HttpMethods.PATCH,
      body
    )
    throw redirect(303, '/finances/money-operation')
  },
} satisfies Actions

export const load = (async ({ params }) => {
  const operation = await unsecuredExternalApiRequest(
    FINANCES_URL + `/finances/money-operation/${params.operationId}`,
    HttpMethods.GET
  )
  return {
    operation: await operation.json(),
  }
}) satisfies PageServerLoad