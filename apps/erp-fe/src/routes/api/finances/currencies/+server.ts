import { unsecuredExternalApiRequest } from "$lib/scripts/httpRequests";
import { HttpMethods } from '$lib/types/httpMethods';
import type { RequestHandler } from "./$types";
import { FINANCES_URL } from '$lib/scripts/urls'

export const GET = (async (event) => {
    const ret = await unsecuredExternalApiRequest(FINANCES_URL + 
        "/finances/currencies" + event.url.search, HttpMethods.GET)
    return new Response(ret.body, {status: 200})
}) satisfies RequestHandler;
