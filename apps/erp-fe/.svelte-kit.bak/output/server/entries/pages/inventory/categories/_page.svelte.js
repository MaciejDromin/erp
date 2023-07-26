import { c as create_ssr_component, d as subscribe, v as validate_component } from "../../../../chunks/ssr.js";
import { P as Pageable } from "../../../../chunks/Pageable.js";
import { C as CategoryTable, c as categoriesStore } from "../../../../chunks/CategoryTable.js";
const Page = create_ssr_component(($$result, $$props, $$bindings, slots) => {
  let $$unsubscribe_categoriesStore;
  $$unsubscribe_categoriesStore = subscribe(categoriesStore, (value) => value);
  $$unsubscribe_categoriesStore();
  return `<div id="category" class="flex flex-col gap-3 px-10 pt-10"><form method="POST" class="mx-auto flex flex-row gap-3 py-6" data-svelte-h="svelte-1lufo66"><input name="name" type="text" placeholder="Type here" class="input input-bordered input-primary w-full max-w-xs"> <button class="btn btn-primary">Add Row</button></form> ${validate_component(Pageable, "Pageable").$$render(
    $$result,
    {
      endpoint: "/inventory/categories",
      component: CategoryTable
    },
    {},
    {}
  )}</div>`;
});
export {
  Page as default
};
