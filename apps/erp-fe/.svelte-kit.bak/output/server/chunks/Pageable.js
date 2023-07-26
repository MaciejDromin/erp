import { c as create_ssr_component, v as validate_component, m as missing_component, b as escape, i as null_to_empty } from "./ssr.js";
const Pageable_svelte_svelte_type_style_lang = "";
const css = {
  code: ".btn-disable.svelte-12glawk{pointer-events:none}",
  map: null
};
const Pageable = create_ssr_component(($$result, $$props, $$bindings, slots) => {
  let { endpoint } = $$props;
  let { component } = $$props;
  let { additionalSearch = void 0 } = $$props;
  let currentPage = 0;
  let maxPage = 0;
  let data;
  const doesLowerPageExists = (page) => {
    if (page == 0)
      return false;
    return true;
  };
  const doesHigherPageExists = (page, mPage) => {
    if (page >= mPage - 1)
      return false;
    return true;
  };
  if ($$props.endpoint === void 0 && $$bindings.endpoint && endpoint !== void 0)
    $$bindings.endpoint(endpoint);
  if ($$props.component === void 0 && $$bindings.component && component !== void 0)
    $$bindings.component(component);
  if ($$props.additionalSearch === void 0 && $$bindings.additionalSearch && additionalSearch !== void 0)
    $$bindings.additionalSearch(additionalSearch);
  $$result.css.add(css);
  return `<div class="flex flex-col"><div class="my-3">${validate_component(component || missing_component, "svelte:component").$$render($$result, Object.assign({}, { data }), {}, {})}</div> <div class="join flex mx-auto"><button class="${escape(
    null_to_empty(`join-item btn disabled:opacity-75 ${!doesLowerPageExists(currentPage) ? "btn-disable opacity-75" : ""}`),
    true
  ) + " svelte-12glawk"}">«</button> <button class="join-item btn btn-disable svelte-12glawk">Page ${escape(currentPage + 1)}</button> <button class="${escape(
    null_to_empty(`join-item btn disabled:opacity-75 ${!doesHigherPageExists(currentPage, maxPage) ? "btn-disable opacity-75" : ""}`),
    true
  ) + " svelte-12glawk"}">»</button></div> </div>`;
});
export {
  Pageable as P
};
