import { c as create_ssr_component, d as subscribe, o as onDestroy, e as each, a as add_attribute, b as escape, f as set_store_value } from "./ssr.js";
import { w as writable } from "./index.js";
let storeInit = [];
const categoriesStore = writable(storeInit);
const CategoryTable = create_ssr_component(($$result, $$props, $$bindings, slots) => {
  let $categoriesStore, $$unsubscribe_categoriesStore;
  $$unsubscribe_categoriesStore = subscribe(categoriesStore, (value) => $categoriesStore = value);
  let { data = void 0 } = $$props;
  let selectedCategoriesIds = /* @__PURE__ */ new Map();
  onDestroy(() => {
    set_store_value(categoriesStore, $categoriesStore = Array.from(selectedCategoriesIds.keys()), $categoriesStore);
  });
  const categorySelectedStyles = (categoriesMap, categoryId) => {
    if (!categoriesMap.has(categoryId))
      return "";
    return "bg-indigo-600 text-white";
  };
  const determineEvenBgColor = (categoriesMap, categoryId) => {
    if (!categoriesMap.has(categoryId))
      return "even:bg-black";
    return "even:bg-indigo-600";
  };
  if ($$props.data === void 0 && $$bindings.data && data !== void 0)
    $$bindings.data(data);
  $$unsubscribe_categoriesStore();
  return `<div class="overflow-x-auto text-primary-content mx-auto"> <table class="table"> <thead class="text-primary-content" data-svelte-h="svelte-50f748"><tr><th>ID</th> <th>Name</th></tr></thead> <tbody>${data !== void 0 ? `${each(data.content, (category) => {
    return `<tr${add_attribute(
      "class",
      `hover:bg-indigo-400 hover:text-black even:text-white hover:even:text-black hover:even:bg-indigo-400 cursor-pointer ease-in transition-all duration-200
                        ${categorySelectedStyles(selectedCategoriesIds, category.id)}
                        ${determineEvenBgColor(selectedCategoriesIds, category.id)}`,
      0
    )}><td>${escape(category.id)}</td> <td>${escape(category.name)}</td> </tr>`;
  })}` : ``}</tbody></table></div> `;
});
export {
  CategoryTable as C,
  categoriesStore as c
};
