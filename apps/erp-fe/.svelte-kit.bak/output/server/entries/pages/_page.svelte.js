import { c as create_ssr_component } from "../../chunks/ssr.js";
const Page = create_ssr_component(($$result, $$props, $$bindings, slots) => {
  return `<div class="text-primary-content w-full h-full" data-svelte-h="svelte-1pzwxls"><h1>Hey!</h1> <p>Hey!</p></div>`;
});
export {
  Page as default
};
