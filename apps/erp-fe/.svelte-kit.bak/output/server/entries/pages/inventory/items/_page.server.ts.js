import { u as unsecuredExternalApiRequest } from "../../../../chunks/httpRequests.js";
import { H as HttpMethods } from "../../../../chunks/httpMethods.js";
const actions = {
  default: async ({ request }) => {
    const data = await request.formData();
    const body = {
      name: data.get("name"),
      quantity: data.get("quantity"),
      unit: data.get("unit"),
      categoryIds: data.getAll("categoryIds")
    };
    await unsecuredExternalApiRequest("/proxy/inventory/items", HttpMethods.POST, body);
  }
};
export {
  actions
};
