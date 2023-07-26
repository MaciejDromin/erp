import { M as MoneyOperationType } from "../../../../chunks/financialTypes.js";
import { u as unsecuredExternalApiRequest } from "../../../../chunks/httpRequests.js";
import { H as HttpMethods } from "../../../../chunks/httpMethods.js";
const actions = {
  create: async ({ request }) => {
    const data = await request.formData();
    const body = {
      plannedAmount: {
        value: data.get("plannedAmount"),
        currencyCode: data.get("currencyCode")
      },
      operationDescription: data.get("operationDescription"),
      plannedYear: data.get("plannedYear"),
      plannedMonth: data.get("plannedMonth"),
      operationCategoryId: data.get("categoryId"),
      operationType: MoneyOperationType.EXPENSES
    };
    await unsecuredExternalApiRequest("/proxy/finances/planned-expenses", HttpMethods.POST, body);
  },
  abandon: async ({ request }) => {
    const data = await request.formData();
    await unsecuredExternalApiRequest(`/proxy/finances/planned-expenses/${data.get("plannedExpensesId")}/abandon`, HttpMethods.PATCH);
  },
  complete: async ({ request }) => {
    const data = await request.formData();
    const body = {
      actualAmount: {
        value: data.get("actualAmount"),
        currencyCode: data.get("currency")
      }
    };
    await unsecuredExternalApiRequest(`/proxy/finances/planned-expenses/${data.get("plannedExpensesId")}/complete`, HttpMethods.PATCH, body);
  }
};
export {
  actions
};
