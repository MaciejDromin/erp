import type { HttpMethods } from "../types/httpMethods"
import { env } from '$env/dynamic/private';

const url = env.NODE_ENV === 'production' ? "http://service-discovery:8080" : "http://127.0.0.1:8080"

const unsecuredExternalApiRequest = async (endpoint: string, method: HttpMethods, body?: any) => {
    const ret = await fetch(url + endpoint, {
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

export { unsecuredExternalApiRequest }