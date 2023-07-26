import { c as create_ssr_component, d as subscribe, o as onDestroy, e as each, a as add_attribute, b as escape, f as set_store_value } from "./ssr.js";
import { w as writable } from "./index.js";
let storeInit = [];
const operationCategoriesStore = writable(storeInit);
const OperationCategoryTable = create_ssr_component(($$result, $$props, $$bindings, slots) => {
  let $operationCategoriesStore, $$unsubscribe_operationCategoriesStore;
  $$unsubscribe_operationCategoriesStore = subscribe(operationCategoriesStore, (value) => $operationCategoriesStore = value);
  let { data = void 0 } = $$props;
  let selectedCategoryIds = /* @__PURE__ */ new Map();
  onDestroy(() => {
    set_store_value(operationCategoriesStore, $operationCategoriesStore = Array.from(selectedCategoryIds.keys()), $operationCategoriesStore);
  });
  const categorySelectedStyles = (categoryMap, categoryId) => {
    if (!categoryMap.has(categoryId))
      return "";
    return "bg-indigo-600 text-white";
  };
  const determineEvenBgColor = (categoryMap, categoryId) => {
    if (!categoryMap.has(categoryId))
      return "even:bg-black";
    return "even:bg-indigo-600";
  };
  if ($$props.data === void 0 && $$bindings.data && data !== void 0)
    $$bindings.data(data);
  $$unsubscribe_operationCategoriesStore();
  return `<div class="overflow-x-auto text-primary-content mx-auto"> <table class="table"> <thead class="text-primary-content" data-svelte-h="svelte-3kea67"><tr><th>ID</th> <th>Operation Name</th> <th>Operation Type</th></tr></thead> <tbody>${data !== void 0 ? `${each(data.content, (operationCategory) => {
    return `<tr${add_attribute(
      "class",
      `hover:bg-indigo-400 hover:text-black even:text-white hover:even:text-black hover:even:bg-indigo-400 cursor-pointer ease-in transition-all duration-200
        ${categorySelectedStyles(selectedCategoryIds, operationCategory.uuid)}
        ${determineEvenBgColor(selectedCategoryIds, operationCategory.uuid)}`,
      0
    )}><td>${escape(operationCategory.uuid)}</td> <td>${escape(operationCategory.operationName)}</td> <td>${escape(operationCategory.operationType)}</td> </tr>`;
  })}` : ``}</tbody></table></div>`;
});
export {
  OperationCategoryTable as O,
  operationCategoriesStore as o
};
