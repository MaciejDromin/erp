import type { RequestHandler } from './$types'

export const GET = (async ({ cookies }) => {
  cookies.delete('Authorization', { path: '/' })
  cookies.delete('Refresh-Token', { path: '/' })
  cookies.delete('X-OrgId', { path: '/' })
  return new Response(null, {
    status: 204,
    headers: { redirected: true, location: '/login' },
  })
}) satisfies RequestHandler
