import { c as create_ssr_component, d as subscribe, o as onDestroy, e as each, a as add_attribute, b as escape, f as set_store_value } from "./ssr.js";
import { w as writable } from "./index.js";
let storeInit = [];
const itemsStore = writable(storeInit);
const ItemTable = create_ssr_component(($$result, $$props, $$bindings, slots) => {
  let $itemsStore, $$unsubscribe_itemsStore;
  $$unsubscribe_itemsStore = subscribe(itemsStore, (value) => $itemsStore = value);
  let { data = void 0 } = $$props;
  let selectedItemsIds = /* @__PURE__ */ new Map();
  onDestroy(() => {
    set_store_value(itemsStore, $itemsStore = Array.from(selectedItemsIds.keys()), $itemsStore);
  });
  const convertCategories = (categories) => {
    let categoryNames = categories.map((category) => category["name"]);
    return categoryNames.toString();
  };
  const itemSelectedStyles = (itemMap, itemId) => {
    if (!itemMap.has(itemId))
      return "";
    return "bg-indigo-600 text-white";
  };
  const determineEvenBgColor = (itemMap, itemId) => {
    if (!itemMap.has(itemId))
      return "even:bg-black";
    return "even:bg-indigo-600";
  };
  if ($$props.data === void 0 && $$bindings.data && data !== void 0)
    $$bindings.data(data);
  $$unsubscribe_itemsStore();
  return `<div class="overflow-x-auto text-primary-content mx-auto"> <table class="table"> <thead class="text-primary-content" data-svelte-h="svelte-nqm7ao"><tr><th>ID</th> <th>Name</th> <th>Quantity</th> <th>Unit</th> <th>Categories</th></tr></thead> <tbody>${data !== void 0 ? `${each(data.content, (item) => {
    return `<tr${add_attribute(
      "class",
      `hover:bg-indigo-400 hover:text-black even:text-white hover:even:text-black hover:even:bg-indigo-400 cursor-pointer ease-in transition-all duration-200
                ${itemSelectedStyles(selectedItemsIds, item.id)}
                ${determineEvenBgColor(selectedItemsIds, item.id)}`,
      0
    )}><td>${escape(item.id)}</td> <td>${escape(item.name)}</td> <td>${escape(item.quantity)}</td> <td>${escape(item.unit)}</td> <td>${escape(convertCategories(item.categories))}</td> </tr>`;
  })}` : ``}</tbody></table></div> `;
});
export {
  ItemTable as I,
  itemsStore as i
};
