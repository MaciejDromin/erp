import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests';
import { HttpMethods } from '$lib/types/httpMethods';
import type { Actions } from './$types';

export const actions = {
    default: async ({ cookies, request }) => {
        const data = await request.formData();
        const body = {
            name: data.get("name")
        }
        await unsecuredExternalApiRequest('/proxy/inventory/categories', HttpMethods.POST, body)
    }
} satisfies Actions;