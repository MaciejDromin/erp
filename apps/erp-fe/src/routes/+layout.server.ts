import determineSidebarItems from '$lib/scripts/determineSidebarItems'
import type { LayoutServerLoad } from './$types'

export const load = (async () => {
  return {
    paths: determineSidebarItems({
      finances: true,
      inventory: true,
      'purchase-scanner': true,
    }),
  }
}) satisfies LayoutServerLoad
