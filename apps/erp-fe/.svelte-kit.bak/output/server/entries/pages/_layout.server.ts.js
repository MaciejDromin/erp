import { u as unsecuredExternalApiRequest } from "../../chunks/httpRequests.js";
import { H as HttpMethods } from "../../chunks/httpMethods.js";
const RECOGNIZED_PATHS = {
  "inventory": {
    name: "Inventory",
    path: "/inventory",
    subPaths: [
      {
        name: "Category",
        path: "/inventory/categories",
        subPaths: []
      },
      {
        name: "Item",
        path: "/inventory/items",
        subPaths: []
      }
    ]
  },
  "finances": {
    name: "Finances",
    path: "/finances",
    subPaths: [
      {
        name: "Currencies",
        path: "/finances/currencies",
        subPaths: []
      },
      {
        name: "Money Operation",
        path: "/finances/money-operation",
        subPaths: []
      },
      {
        name: "Periodical Money Operations",
        path: "/finances/money-operation/periodical",
        subPaths: []
      },
      {
        name: "Object Value",
        path: "/finances/object-value",
        subPaths: []
      },
      {
        name: "Operation Category",
        path: "/finances/operation-category",
        subPaths: []
      },
      {
        name: "Planned Expenses",
        path: "/finances/planned-expenses",
        subPaths: []
      }
    ]
  }
};
function determineSidebarItems(registeredItems) {
  const retArr = [];
  registeredItems.forEach((registeredItem) => {
    if (RECOGNIZED_PATHS[registeredItem.name] !== void 0) {
      retArr.push(RECOGNIZED_PATHS[registeredItem.name]);
    }
  });
  return retArr;
}
const load = async () => {
  const ret = await unsecuredExternalApiRequest("/register", HttpMethods.GET);
  return {
    paths: determineSidebarItems(await ret.json())
  };
};
export {
  load
};
