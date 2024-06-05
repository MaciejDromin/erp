import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests';
import { HttpMethods } from '$lib/types/httpMethods';
import type { Actions } from './$types';
import { FINANCES_URL } from '$lib/scripts/urls'

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
            nextApplicableMonth: data.get("nextApplicableMonth"),
            repetitionPeriod: data.get("repetitionPeriod"),
            operationCategoryId: data.get("categoryId")
        }
        await unsecuredExternalApiRequest(FINANCES_URL +
            '/finances/money-operation/periodical', HttpMethods.POST, body)
    }
} satisfies Actions;
