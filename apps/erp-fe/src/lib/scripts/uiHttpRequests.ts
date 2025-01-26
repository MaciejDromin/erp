import type { HttpMethods } from '../types/httpMethods'

const apiRequest = async (
  endpoint: string,
  method: HttpMethods,
  body?: any
) => {
  const ret = await fetch('/api' + endpoint, {
    method: method,
    body: JSON.stringify(body),
    headers: {
      Accept: 'application/json',
      'Content-Type': 'application/json',
    },
  })
  if (ret.status === 204 && ret.headers.get('redirected') === 'true')
    window.location.href = ret.headers.get('location')
  return ret
}

const apiRequestFile = async (
  endpoint: string,
  method: HttpMethods,
  filename: string,
  body?: any
) => {
  return await fetch('/api' + endpoint, {
    method: method,
    body: body,
    headers: {
      Accept: 'application/json',
      'Content-Type': 'application/octet-stream',
      filename: filename,
    },
  })
}

export { apiRequest, apiRequestFile }
