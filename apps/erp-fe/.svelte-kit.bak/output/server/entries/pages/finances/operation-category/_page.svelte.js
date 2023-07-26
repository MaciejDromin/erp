import { c as create_ssr_component, e as each, v as validate_component, a as add_attribute, b as escape } from "../../../../chunks/ssr.js";
import { P as Pageable } from "../../../../chunks/Pageable.js";
import { O as OperationCategoryTable } from "../../../../chunks/OperationCategoryTable.js";
import { M as MoneyOperationType } from "../../../../chunks/financialTypes.js";
const Page = create_ssr_component(($$result, $$props, $$bindings, slots) => {
  return `<div id="item" class="flex flex-col gap-3 px-10 pt-10"><form method="POST" class="mx-auto flex flex-col gap-3 py-6"><div class="flex flex-row gap-3"><select name="operationType" class="select select-primary w-full max-w-xs">${each(Object.values(MoneyOperationType), (operationType) => {
    return `<option${add_attribute("value", operationType, 0)}>${escape(operationType)}</option>`;
  })}</select> <input name="operationName" type="text" placeholder="Operation Name" class="input input-bordered input-primary w-full max-w-xs"></div> <button class="btn btn-primary" data-svelte-h="svelte-1q26zcv">Add Row</button></form> ${validate_component(Pageable, "Pageable").$$render(
    $$result,
    {
      endpoint: "/finances/operation-category",
      component: OperationCategoryTable
    },
    {},
    {}
  )}</div>`;
});
export {
  Page as default
};
