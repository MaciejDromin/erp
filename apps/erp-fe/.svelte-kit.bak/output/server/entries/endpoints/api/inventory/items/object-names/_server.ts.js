import { u as unsecuredExternalApiRequest } from "../../../../../../chunks/httpRequests.js";
import { H as HttpMethods } from "../../../../../../chunks/httpMethods.js";
const POST = async ({ request }) => {
  const ret = await unsecuredExternalApiRequest("/proxy/inventory/items/object-names", HttpMethods.POST, await request.json());
  return new Response(ret.body, { status: 200 });
};
export {
  POST
};
