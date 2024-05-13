import { unsecuredExternalApiRequest } from '$lib/scripts/httpRequests';
import { HttpMethods } from '$lib/types/httpMethods';
import type { Actions } from './$types';
import type { PageServerLoad } from './$types';

export const actions = {
    default: async ({ request }) => {
        const data = await request.formData();
        const body = {
            amount: data.get("amount"),
            currencyCode: data.get("currencyCode"),
            objectId: data.get("itemId")
        }
        await unsecuredExternalApiRequest('/proxy/finances/object-value', HttpMethods.POST, body)
    }
} satisfies Actions;

export const load = (async ({ params }) => {
    const objectIds = await unsecuredExternalApiRequest('/proxy/finances/object-value/object-ids', HttpMethods.GET)
    const objectIdsBody = {
        itemIds: await objectIds.json()
    }
    const countMap = await unsecuredExternalApiRequest('/proxy/inventory/items/object-count', HttpMethods.POST, objectIdsBody)
    const data = await unsecuredExternalApiRequest('/proxy/finances/object-value/total-value', HttpMethods.POST, await countMap.json())
    return {
        objectsValue: await data.json(),
        objectIds: objectIdsBody.itemIds
    };
}) satisfies PageServerLoad;
