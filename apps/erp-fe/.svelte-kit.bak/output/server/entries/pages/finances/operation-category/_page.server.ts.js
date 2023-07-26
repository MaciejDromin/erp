import { u as unsecuredExternalApiRequest } from "../../../../chunks/httpRequests.js";
import { H as HttpMethods } from "../../../../chunks/httpMethods.js";
const actions = {
  default: async ({ request }) => {
    const data = await request.formData();
    const body = {
      operationType: data.get("operationType"),
      operationName: data.get("operationName")
    };
    await unsecuredExternalApiRequest("/proxy/finances/operation-category", HttpMethods.POST, body);
  }
};
export {
  actions
};
