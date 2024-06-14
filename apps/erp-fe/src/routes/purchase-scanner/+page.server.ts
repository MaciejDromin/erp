import { unsecuredExternalApiRequestFileUpload } from '$lib/scripts/httpRequests';
import { HttpMethods } from '$lib/types/httpMethods';
import type { Actions } from './$types';
import { PURCHASE_SCANNER_URL } from '$lib/scripts/urls'

export const actions = {
    default: async ({ request }) => {
        const data = await request.formData();
        await unsecuredExternalApiRequestFileUpload(PURCHASE_SCANNER_URL +
            '/receipts', HttpMethods.POST, data.get('file'))
    }
} satisfies Actions;
