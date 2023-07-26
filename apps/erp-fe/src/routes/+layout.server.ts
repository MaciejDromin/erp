import determineSidebarItems from '$lib/scripts/determineSidebarItems';
import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests';
import { HttpMethods } from '$lib/types/httpMethods';
import type { LayoutServerLoad } from './$types';

export const load = (async () => {
    const ret = await unsecuredExternalApiRequest('/register', HttpMethods.GET)
    return {
        paths: determineSidebarItems(await ret.json())
    };
}) satisfies LayoutServerLoad;