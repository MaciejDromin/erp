import { unsecuredExternalApiRequest } from "$lib/scripts/httpRequests";
import { HttpMethods } from '$lib/types/httpMethods';
import type { RequestHandler } from "./$types";
import { PLANNER_URL } from '$lib/scripts/urls'

export const GET = (async (event) => {
    const ret = await unsecuredExternalApiRequest(PLANNER_URL + 
        "/planner/materials" + event.url.search, HttpMethods.GET)
    return new Response(ret.body, {status: 200})
}) satisfies RequestHandler;
