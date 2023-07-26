import { unsecuredExternalApiRequest } from "$lib/scripts/httpRequests";
import { HttpMethods } from '$lib/types/httpMethods';
import type { RequestHandler } from "./$types";

export const POST = (async ({ request }) => {
    const ret = await unsecuredExternalApiRequest("/proxy/inventory/items/object-names", HttpMethods.POST, await request.json())
    return new Response(ret.body, {status: 200})
}) satisfies RequestHandler;