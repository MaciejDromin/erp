import { unsecuredExternalApiRequest } from "$lib/scripts/httpRequests";
import { HttpMethods } from '$lib/types/httpMethods';
import type { RequestHandler } from "./$types";

export const GET = (async (event) => {
    const ret = await unsecuredExternalApiRequest("/proxy/finances/object-value" + event.url.search, HttpMethods.GET)
    return new Response(ret.body, {status: 200})
}) satisfies RequestHandler;