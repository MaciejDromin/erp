import { u as unsecuredExternalApiRequest } from "../../../../chunks/httpRequests.js";
import { H as HttpMethods } from "../../../../chunks/httpMethods.js";
const actions = {
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
    };
    await unsecuredExternalApiRequest("/proxy/finances/money-operation", HttpMethods.POST, body);
  }
};
export {
  actions
};
