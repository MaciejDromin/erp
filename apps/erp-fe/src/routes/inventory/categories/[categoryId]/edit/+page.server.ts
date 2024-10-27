import type { PageServerLoad } from './$types'
import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests'
import { HttpMethods } from '$lib/types/httpMethods'
import { INVENTORY_URL } from '$lib/scripts/urls'
import { redirect } from '@sveltejs/kit'

export const actions = {
  default: async ({ cookies, request }) => {
    const data = await request.formData()
    const body = {
      id: data.get('categoryId'),
      name: data.get('name'),
    }
    await unsecuredExternalApiRequest(
      INVENTORY_URL + `/inventory/categories/${body.id}`,
      HttpMethods.PATCH,
      body
    )
    throw redirect(303, '/inventory/categories')
  },
} satisfies Actions

export const load = (async ({ params }) => {
  const category = await unsecuredExternalApiRequest(
    INVENTORY_URL + `/inventory/categories/${params.categoryId}`,
    HttpMethods.GET
  )
  return {
    category: await category.json(),
  }
}) satisfies PageServerLoad
