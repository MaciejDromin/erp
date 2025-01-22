import { redirect, fail } from '@sveltejs/kit'

export const actions = {
  default: async ({ cookies, request }) => {
    console.log(cookies.get("test"))
    throw redirect(303, '/inventory/addresses')
  },
} satisfies Actions
