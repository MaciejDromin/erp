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
      uuid: data.get('plannedExpenseId'),
      plannedAmount: {
        value: data.get('plannedAmount'),
        currencyCode: data.get('currencyCode'),
      },
      operationDescription: data.get('operationDescription'),
      plannedYear: Number(data.get('plannedYear')),
      plannedMonth: data.get('plannedMonth'),
      operationCategoryId: category.uuid,
    }
    await unsecuredExternalApiRequest(
      FINANCES_URL + `/finances/planned-expenses/${body.uuid}`,
      HttpMethods.PATCH,
      body
    )
    throw redirect(303, '/finances/planned-expenses')
  },
} satisfies Actions

export const load = (async ({ params }) => {
  const plannedExpense = await unsecuredExternalApiRequest(
    FINANCES_URL + `/finances/planned-expenses/${params.plannedExpenseId}`,
    HttpMethods.GET
  )
  return {
    plannedExpense: await plannedExpense.json(),
  }
}) satisfies PageServerLoad
