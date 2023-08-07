import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests';
import { HttpMethods } from '$lib/types/httpMethods';
import type { Actions } from './$types';

export const actions = {
    default: async ({ cookies, request }) => {
        const data = await request.formData();
        const body = {
            name: data.get("name"),
            unit: data.get("unit"),
            unitAmount: {
                value: data.get("value"),
                currencyCode: data.get("currency")
            },
            source: data.get("source")
        }
        await unsecuredExternalApiRequest('/proxy/planner/materials', HttpMethods.POST, body)
    }
} satisfies Actions;