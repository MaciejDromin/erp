import { c as create_ssr_component, e as each, b as escape, a as add_attribute, d as subscribe, v as validate_component } from "../../../../chunks/ssr.js";
import { M as Modal } from "../../../../chunks/Modal.js";
import { P as Pageable } from "../../../../chunks/Pageable.js";
import { o as operationCategoriesStore, O as OperationCategoryTable } from "../../../../chunks/OperationCategoryTable.js";
import { a as Month, M as MoneyOperationType } from "../../../../chunks/financialTypes.js";
const PlannedExpensesTable = create_ssr_component(($$result, $$props, $$bindings, slots) => {
  let { data = void 0 } = $$props;
  let actualAmountByIdMap = {};
  if ($$props.data === void 0 && $$bindings.data && data !== void 0)
    $$bindings.data(data);
  return `<div class="overflow-x-auto text-primary-content mx-auto"><table class="table"><thead class="text-primary-content" data-svelte-h="svelte-12vm1v"><tr><th>ID</th> <th>Operation Category</th> <th>Operation Description</th> <th>Planned Amount</th> <th>Actual Amount</th> <th>Currency</th> <th>Planned Year</th> <th>Planned Month</th> <th>Status</th> <th>Finalized Date</th> <th>Actions</th></tr></thead> <tbody>${data !== void 0 ? `${each(data.content, (plannedExpenses) => {
    return `<tr><td>${escape(plannedExpenses.uuid)}</td> <td>${escape(plannedExpenses.operationCategory.operationName)}</td> <td>${escape(plannedExpenses.operationDescription)}</td> <td>${escape(plannedExpenses.plannedAmount.value)}</td> <td>${plannedExpenses.plannedExpensesStatus === "PLANNED" ? `<input name="amountHolder" class="input input-bordered input-primary w-full max-w-xs text-white" type="text"${add_attribute("value", actualAmountByIdMap[plannedExpenses.uuid], 0)}>` : `${escape(plannedExpenses.actualAmount.value)}`}</td> <td>${escape(plannedExpenses.plannedAmount.currencyCode)}</td> <td>${escape(plannedExpenses.plannedYear)}</td> <td>${escape(plannedExpenses.plannedMonth)}</td> <td>${escape(plannedExpenses.plannedExpensesStatus)}</td> <td>${escape(plannedExpenses.finalizedDate === void 0 ? "No Value" : plannedExpenses.finalizedDate)}</td> <td>${plannedExpenses.plannedExpensesStatus !== "PLANNED" ? `No Actions` : `<div class="flex flex-row gap-3"><form method="POST" action="?/complete"><input name="plannedExpensesId" class="hidden"${add_attribute("value", plannedExpenses.uuid, 0)} type="text"> <input name="actualAmount" class="hidden" type="text"${add_attribute("value", actualAmountByIdMap[plannedExpenses.uuid], 0)}> <input name="currency" class="hidden"${add_attribute("value", plannedExpenses.plannedAmount.currencyCode, 0)} type="text"> <button class="btn btn-primary mx-auto" data-svelte-h="svelte-1lp76g2">Complete</button></form> <form method="POST" action="?/abandon"><input name="plannedExpensesId" class="hidden"${add_attribute("value", plannedExpenses.uuid, 0)} type="text"> <button class="btn btn-primary mx-auto" data-svelte-h="svelte-hgleey">Abandon</button></form> </div>`}</td> </tr>`;
  })}` : ``}</tbody></table></div>`;
});
const Page = create_ssr_component(($$result, $$props, $$bindings, slots) => {
  let $$unsubscribe_operationCategoriesStore;
  $$unsubscribe_operationCategoriesStore = subscribe(operationCategoriesStore, (value) => value);
  let categories = [];
  operationCategoriesStore.subscribe((cat) => {
    categories = [...cat];
    categories = categories;
  });
  const determineButtonName = (arr) => {
    if (arr.length === 0)
      return "select category";
    return `${arr.length} category selected`;
  };
  $$unsubscribe_operationCategoriesStore();
  return `<div id="item" class="flex flex-col gap-3 px-10 pt-10"><form method="POST" class="mx-auto flex flex-col gap-3 py-6" action="?/create"><div class="flex flex-row gap-3" data-svelte-h="svelte-1tq5v7a"><input name="plannedAmount" type="text" placeholder="Planned Amount" class="input input-bordered input-primary w-full max-w-xs"> <input name="currencyCode" type="text" placeholder="Currency Code" class="input input-bordered input-primary w-full max-w-xs"></div> <div class="flex flex-row gap-3" data-svelte-h="svelte-fk5lk0"><input name="operationDescription" type="text" placeholder="Operation Description" class="input input-bordered input-primary w-full"></div> <div class="flex flex-row gap-3"><input name="plannedYear" type="text" placeholder="Planned Year" class="input input-bordered input-primary w-full max-w-xs"> <select name="plannedMonth" class="select select-primary w-full max-w-xs">${each(Object.values(Month), (month) => {
    return `<option${add_attribute("value", month, 0)}>${escape(month)}</option>`;
  })}</select></div> <div class="flex flex-row gap-3"><select multiple name="categoryId" class="p-4 mr-auto hidden">${each(categories, (category) => {
    return `<option${add_attribute("value", category, 0)}></option>`;
  })}</select> <div class="mx-auto">${validate_component(Modal, "Modal").$$render(
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
            endpoint: "/finances/operation-category",
            component: OperationCategoryTable,
            additionalSearch: `&operationType=${MoneyOperationType.EXPENSES}`
          },
          {},
          {}
        )}`;
      }
    }
  )}</div></div> <button class="btn btn-primary mx-auto" data-svelte-h="svelte-24e0po">Add Row</button></form> ${validate_component(Pageable, "Pageable").$$render(
    $$result,
    {
      endpoint: "/finances/planned-expenses",
      component: PlannedExpensesTable
    },
    {},
    {}
  )}</div>`;
});
export {
  Page as default
};
