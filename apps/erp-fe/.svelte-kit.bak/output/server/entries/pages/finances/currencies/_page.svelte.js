import { c as create_ssr_component, e as each, b as escape, v as validate_component } from "../../../../chunks/ssr.js";
import { P as Pageable } from "../../../../chunks/Pageable.js";
const CurrencyTable = create_ssr_component(($$result, $$props, $$bindings, slots) => {
  let { data = void 0 } = $$props;
  if ($$props.data === void 0 && $$bindings.data && data !== void 0)
    $$bindings.data(data);
  return `<div class="overflow-x-auto text-primary-content mx-auto"> <table class="table"> <thead class="text-primary-content" data-svelte-h="svelte-10ghxv1"><tr><th>Code</th> <th>Currency</th> <th>Rate</th> <th>Effective Date</th></tr></thead> <tbody>${data !== void 0 ? `${each(data.content, (currency) => {
    return `<tr><td>${escape(currency.code)}</td> <td>${escape(currency.currency)}</td> <td>${escape(currency.mid)}</td> <td>${escape(currency.effectiveDate)}</td> </tr>`;
  })}` : ``}</tbody></table></div>`;
});
const Page = create_ssr_component(($$result, $$props, $$bindings, slots) => {
  return `<div id="category" class="flex flex-col gap-3 px-10 pt-10">${validate_component(Pageable, "Pageable").$$render(
    $$result,
    {
      endpoint: "/finances/currencies",
      component: CurrencyTable
    },
    {},
    {}
  )}</div>`;
});
export {
  Page as default
};
