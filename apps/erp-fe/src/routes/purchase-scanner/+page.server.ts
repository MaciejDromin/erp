import { unsecuredExternalApiRequestFileUpload } from '$lib/scripts/httpRequests';
import { HttpMethods } from '$lib/types/httpMethods';
import type { Actions } from './$types';
import { PURCHASE_SCANNER_URL } from '$lib/scripts/urls'

export const actions = {
    default: async ({ request }) => {
        const data = await request.formData();
        const body = new FormData();
        body.append('file', data.get('file'));
        const headers = {
            'Accept': 'application/json'
        }
        const ret = await unsecuredExternalApiRequestFileUpload(PURCHASE_SCANNER_URL +
            '/receipts', HttpMethods.POST, body, headers)
    }
} satisfies Actions;
