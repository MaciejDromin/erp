import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests';
import { HttpMethods } from '$lib/types/httpMethods';
import type { Actions } from './$types';
import { PLANNER_URL } from '$lib/scripts/urls'

export const actions = {
    default: async ({ cookies, request }) => {
        const data = await request.formData();
        const creation = data.get("creation")
        await unsecuredExternalApiRequest(PLANNER_URL +
            '/planner/investments', HttpMethods.POST, JSON.parse(creation?.toString()!))
    }
} satisfies Actions;
