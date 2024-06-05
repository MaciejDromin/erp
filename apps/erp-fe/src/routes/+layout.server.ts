import determineSidebarItems from '$lib/scripts/determineSidebarItems';
import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests';
import { HttpMethods } from '$lib/types/httpMethods';
import type { LayoutServerLoad } from './$types';
import { CONSUL_URL } from '$lib/scripts/urls'

export const load = (async () => {
    const ret = await unsecuredExternalApiRequest(CONSUL_URL + '/v1/catalog/services', HttpMethods.GET)
    return {
        paths: determineSidebarItems(await ret.json())
    };
}) satisfies LayoutServerLoad;
