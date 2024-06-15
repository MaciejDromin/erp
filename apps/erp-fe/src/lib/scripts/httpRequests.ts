import type { HttpMethods } from "../types/httpMethods"
import { env } from '$env/dynamic/private';

const unsecuredExternalApiRequest = async (url: string, method: HttpMethods, body?: any) => {
    const ret = await fetch(url, {
        method: method,
        body: JSON.stringify(body),
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    }).catch(e => {
        console.log(e)
        return new Response(null, {status: 503})
    })
    return ret
}

const unsecuredExternalApiRequestFileUpload = async (url: string, method: HttpMethods, body: any, headers: any) => {
    const ret = await fetch(url, {
        method: method,
        body: body,
        headers: headers
    }).catch(e => {
        console.log(e)
        return new Response(null, {status: 503})
    })
    return ret
}

export { unsecuredExternalApiRequest, unsecuredExternalApiRequestFileUpload  }
