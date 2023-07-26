import { c as create_ssr_component, e as each, b as escape, d as subscribe, v as validate_component, a as add_attribute } from "../../../../../chunks/ssr.js";
import { M as Modal } from "../../../../../chunks/Modal.js";
import { P as Pageable } from "../../../../../chunks/Pageable.js";
import { o as operationCategoriesStore, O as OperationCategoryTable } from "../../../../../chunks/OperationCategoryTable.js";
import { M as MoneyOperationType, a as Month } from "../../../../../chunks/financialTypes.js";
const PeriodicalMoneyOperationTable = create_ssr_component(($$result, $$props, $$bindings, slots) => {
  let { data = void 0 } = $$props;
  if ($$props.data === void 0 && $$bindings.data && data !== void 0)
    $$bindings.data(data);
  return `<div class="overflow-x-auto text-primary-content mx-auto"> <table class="table"> <thead class="text-primary-content" data-svelte-h="svelte-qukcdu"><tr><th>ID</th> <th>Operation Type</th> <th>Operation Category</th> <th>Operation Description</th> <th>Amount</th> <th>Currency</th> <th>Next Applicable Month</th></tr></thead> <tbody>${data !== void 0 ? `${each(data.content, (periodical) => {
    return `<tr><td>${escape(periodical.uuid)}</td> <td>${escape(periodical.operationCategory.operationType)}</td> <td>${escape(periodical.operationCategory.operationName)}</td> <td>${escape(periodical.operationDescription)}</td> <td>${escape(periodical.amount.value)}</td> <td>${escape(periodical.amount.currencyCode)}</td> <td>${escape(periodical.nextApplicableMonth)}</td> </tr>`;
  })}` : ``}</tbody></table></div>`;
});
const Page = create_ssr_component(($$result, $$props, $$bindings, slots) => {
  let $$unsubscribe_operationCategoriesStore;
  $$unsubscribe_operationCategoriesStore = subscribe(operationCategoriesStore, (value) => value);
  let categories = [];
  let operationType = MoneyOperationType.INCOME;
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
  return `<div id="item" class="flex flex-col gap-3 px-10 pt-10"><form method="POST" class="mx-auto flex flex-col gap-3 py-6"><div class="flex flex-row gap-3" data-svelte-h="svelte-oygg30"><input name="amount" type="text" placeholder="Amount" class="input input-bordered input-primary w-full max-w-xs"> <input name="currencyCode" type="text" placeholder="Currency Code" class="input input-bordered input-primary w-full max-w-xs"></div> <div class="flex flex-row gap-3" data-svelte-h="svelte-fk5lk0"><input name="operationDescription" type="text" placeholder="Operation Description" class="input input-bordered input-primary w-full"></div> <div class="flex flex-row gap-3"><select name="nextApplicableMonth" class="select select-primary w-full max-w-xs">${each(Object.values(Month), (month) => {
    return `<option${add_attribute("value", month, 0)}>${escape(month)}</option>`;
  })}</select> <input name="repetitionPeriod" type="text" placeholder="Repetition Period" class="input input-bordered input-primary w-full max-w-xs"></div> <div class="flex flex-row gap-3"><select name="operationType" class="select select-primary w-full max-w-xs">${each(Object.values(MoneyOperationType), (operationType2) => {
    return `<option${add_attribute("value", operationType2, 0)}>${escape(operationType2)}</option>`;
  })}</select> <select multiple name="categoryId" class="p-4 mr-auto hidden">${each(categories, (category) => {
    return `<option${add_attribute("value", category, 0)}></option>`;
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
            endpoint: "/finances/operation-category",
            component: OperationCategoryTable,
            additionalSearch: `&operationType=${operationType}`
          },
          {},
          {}
        )}`;
      }
    }
  )}</div></div> <button class="btn btn-primary mx-auto" data-svelte-h="svelte-24e0po">Add Row</button></form> ${validate_component(Pageable, "Pageable").$$render(
    $$result,
    {
      endpoint: "/finances/money-operation/periodical",
      component: PeriodicalMoneyOperationTable
    },
    {},
    {}
  )}</div>`;
});
export {
  Page as default
};
