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
      id: data.get('periodicalOperationId'),
      amount: {
        value: data.get('amount'),
        currencyCode: data.get('currencyCode'),
      },
      operationType: data.get('operationType'),
      operationDescription: data.get('operationDescription'),
      repetitionPeriod: Number(data.get('repetitionPeriod')),
      nextApplicableMonth: data.get('nextApplicableMonth'),
      operationCategoryId: category.id,
    }
    await unsecuredExternalApiRequest(
      FINANCES_URL + `/finances/money-operation/periodical/${body.id}`,
      HttpMethods.PATCH,
      body
    )
    throw redirect(303, '/finances/money-operation/periodical')
  },
} satisfies Actions

export const load = (async ({ params }) => {
  const operation = await unsecuredExternalApiRequest(
    FINANCES_URL +
      `/finances/money-operation/periodical/${params.periodicalOperationId}`,
    HttpMethods.GET
  )
  return {
    operation: await operation.json(),
  }
}) satisfies PageServerLoad
