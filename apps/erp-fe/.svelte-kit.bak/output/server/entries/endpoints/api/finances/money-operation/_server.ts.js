import { u as unsecuredExternalApiRequest } from "../../../../../chunks/httpRequests.js";
import { H as HttpMethods } from "../../../../../chunks/httpMethods.js";
const GET = async (event) => {
  const ret = await unsecuredExternalApiRequest("/proxy/finances/money-operation" + event.url.search, HttpMethods.GET);
  return new Response(ret.body, { status: 200 });
};
export {
  GET
};
