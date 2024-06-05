import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests';
import { HttpMethods } from '$lib/types/httpMethods';
import type { Actions } from './$types';
import { FINANCES_URL } from '$lib/scripts/urls'

export const actions = {
    default: async ({ request }) => {
        const data = await request.formData();
        const body = {
            operationType: data.get("operationType"),
            operationName: data.get("operationName")
        }
        await unsecuredExternalApiRequest(FINANCES_URL +
            '/finances/operation-category', HttpMethods.POST, body)
    }
} satisfies Actions;
