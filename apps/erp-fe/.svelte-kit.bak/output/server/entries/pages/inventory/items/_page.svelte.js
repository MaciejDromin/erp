import { c as create_ssr_component, d as subscribe, e as each, v as validate_component, a as add_attribute, b as escape } from "../../../../chunks/ssr.js";
import { c as categoriesStore, C as CategoryTable } from "../../../../chunks/CategoryTable.js";
import { I as ItemTable } from "../../../../chunks/ItemTable.js";
import { M as Modal } from "../../../../chunks/Modal.js";
import { P as Pageable } from "../../../../chunks/Pageable.js";
var ItemUnit = /* @__PURE__ */ ((ItemUnit2) => {
  ItemUnit2["M3"] = "M3";
  ItemUnit2["M2"] = "M2";
  ItemUnit2["SZT"] = "SZT";
  ItemUnit2["L"] = "L";
  return ItemUnit2;
})(ItemUnit || {});
const Page = create_ssr_component(($$result, $$props, $$bindings, slots) => {
  let $$unsubscribe_categoriesStore;
  $$unsubscribe_categoriesStore = subscribe(categoriesStore, (value) => value);
  let categories = [];
  categoriesStore.subscribe((cat) => {
    categories = [...cat];
    categories = categories;
  });
  const determineButtonName = (arr) => {
    if (arr.length === 0)
      return "select categories";
    return `${arr.length} categories selected`;
  };
  $$unsubscribe_categoriesStore();
  return `<div id="item" class="flex flex-col gap-3 px-10 pt-10"><form method="POST" class="mx-auto flex flex-col gap-3 py-6"><div class="flex flex-row gap-3" data-svelte-h="svelte-1rf2mv4"><input name="name" type="text" placeholder="Name" class="input input-bordered input-primary w-full max-w-xs"> <input name="quantity" type="text" placeholder="Quantity" class="input input-bordered input-primary w-full max-w-xs"></div> <div class="flex flex-row gap-3"><select multiple name="categoryIds" class="p-4 mr-auto hidden">${each(categories, (category) => {
    return `<option${add_attribute("value", category, 0)}></option>`;
  })}</select> <select name="unit" class="select select-primary w-full max-w-xs">${each(Object.values(ItemUnit), (unit) => {
    return `<option${add_attribute("value", unit, 0)}>${escape(unit)}</option>`;
  })}</select> <div class="mr-auto">${validate_component(Modal, "Modal").$$render(
    $$result,
    {
      modalId: "category_modal",
      buttonName: determineButtonName(categories)
    },
    {},
    {
      default: () => {
        return `${validate_component(Pageable, "Pageable").$$render(
          $$result,
          {
            endpoint: "/inventory/categories",
            component: CategoryTable
          },
          {},
          {}
        )}`;
      }
    }
  )}</div> <button class="btn btn-primary" data-svelte-h="svelte-1q26zcv">Add Row</button></div></form> ${validate_component(Pageable, "Pageable").$$render(
    $$result,
    {
      endpoint: "/inventory/items",
      component: ItemTable
    },
    {},
    {}
  )}</div>`;
});
export {
  Page as default
};
