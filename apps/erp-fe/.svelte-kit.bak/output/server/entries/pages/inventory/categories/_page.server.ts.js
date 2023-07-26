import { u as unsecuredExternalApiRequest } from "../../../../chunks/httpRequests.js";
import { H as HttpMethods } from "../../../../chunks/httpMethods.js";
const actions = {
  default: async ({ cookies, request }) => {
    const data = await request.formData();
    const body = {
      name: data.get("name")
    };
    await unsecuredExternalApiRequest("/proxy/inventory/categories", HttpMethods.POST, body);
  }
};
export {
  actions
};
