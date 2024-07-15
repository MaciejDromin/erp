import type { HttpMethods } from '../types/httpMethods'

const apiRequest = async (
  endpoint: string,
  method: HttpMethods,
  body?: any
) => {
  return await fetch('/api' + endpoint, {
    method: method,
    body: JSON.stringify(body),
    headers: {
      Accept: 'application/json',
      'Content-Type': 'application/json',
    },
  })
}

export { apiRequest }
