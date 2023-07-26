import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests';
import { HttpMethods } from '$lib/types/httpMethods';
import type { Actions } from './$types';


export const actions = {
    default: async ({ request }) => {
        const data = await request.formData();
        const body = {
            amount: {
                value: data.get("amount"),
                currencyCode: data.get("currencyCode")
            },
            operationType: data.get("operationType"),
            operationDescription: data.get("operationDescription"),
            operationCategoryId: data.get("categoryId")
        }
        await unsecuredExternalApiRequest('/proxy/finances/money-operation', HttpMethods.POST, body)
    }
} satisfies Actions;