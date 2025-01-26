import type { HttpMethods } from '../types/httpMethods'
import { redirect } from '@sveltejs/kit'
import { AUTH_URL } from '$lib/scripts/urls.ts'

const unsecuredExternalApiRequest = async (
  url: string,
  method: HttpMethods,
  body?: any
) => {
  const ret = await fetch(url, {
    method: method,
    body: JSON.stringify(body),
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json',
    },
  }).catch((e) => {
    console.log(e)
    return new Response(null, { status: 503 })
  })
  return ret
}

const securedExternalApiRequestFileUpload = async (
  url: string,
  method: HttpMethods,
  headers: any,
  authHeader: string,
  cookies: any,
  body: any
) => {
  if (authHeader === undefined) {
    if (cookies.get('Refresh-Token') !== undefined) {
      const newAuthToken = tryRefreshingToken(cookies)
      return securedExternalApiRequestFileUpload(url, method, headers, newAuthToken, cookies, body)
    }
    return new Response(null, {
      status: 204,
      headers: { location: '/login', redirected: true },
    })
  }
  headers['Authorization'] = authHeader
  const ret = await fetch(url, {
    method: method,
    body: body,
    headers: headers,
    duplex: 'half',
  }).catch((e) => {
    console.log(e)
    return new Response(null, { status: 503 })
  })
  if (ret.status === 401) {
    const newAuthToken = tryRefreshingToken(cookies)
    return securedExternalApiRequestFileUpload(url, method, headers, newAuthToken, cookies, body)
  }
  return ret
}

const securedExternalApiRequest = async (
  url: string,
  method: HttpMethods,
  authHeader: string,
  cookies: any,
  body?: any
) => {
  if (authHeader === undefined) {
    if (cookies.get('Refresh-Token') !== undefined) {
      const newAuthToken = tryRefreshingToken(cookies)
      return securedExternalApiRequest(url, method, newAuthToken, cookies, body)
    }
    return new Response(null, {
      status: 204,
      headers: { location: '/login', redirected: true },
    })
  }
  const ret = await fetch(url, {
    method: method,
    body: JSON.stringify(body),
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json',
      'Authorization': authHeader,
    },
  }).catch((e) => {
    // IF token expired or something, return new Response to login page
    // Otherwise, just return a generic error response
    console.log(e)
    return new Response(null, { status: 503 })
  })
  if (ret.status === 401) {
    const newAuthToken = tryRefreshingToken(cookies)
    return securedExternalApiRequest(url, method, newAuthToken, cookies, body)
  }
  return ret
}

const tryRefreshingToken = async (cookies) => {
  const refreshToken = cookies.get('Refresh-Token')
  if (refreshToken === undefined)
    return new Response(null, {
      status: 204,
      headers: { location: '/login', redirected: true },
    })
  const refreshResp = await fetch(`${AUTH_URL}/token`, {
    method: HttpMethods.POST,
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json',
      'Refresh-Token': refreshToken,
    },
  }).catch((e) => {
    // throw fail
  })
  if (refreshResp.status === 401)
    return new Response(null, {
      status: 204,
      headers: { location: '/login', redirected: true },
    })
  const respBody = await resp.json()
  let expiresAt = new Date(0)
  expiresAt.setUTCSeconds(respBody.expiresIn)
  const opts = {
    path: '/',
    expires: expiresAt,
  }
  const newAuthToken = `Bearer ${respBody.authToken}`
  cookies.set('Authorization', newAuthToken, opts)
  cookies.set('Refresh-Token', respBody.refreshToken, opts)
  cookies.set('X-OrgId', respBody.orgId, opts)
  return newAuthToken
}

export {
  unsecuredExternalApiRequest,
  unsecuredExternalApiRequestFileUpload,
  securedExternalApiRequest,
}
