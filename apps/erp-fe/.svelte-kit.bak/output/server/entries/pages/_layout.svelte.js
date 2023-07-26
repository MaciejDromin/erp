import { c as create_ssr_component, e as each, a as add_attribute, b as escape, v as validate_component, d as subscribe } from "../../chunks/ssr.js";
import { p as page } from "../../chunks/stores.js";
const app = "";
const Breadcrumbs = create_ssr_component(($$result, $$props, $$bindings, slots) => {
  let { breadcrumbs } = $$props;
  if ($$props.breadcrumbs === void 0 && $$bindings.breadcrumbs && breadcrumbs !== void 0)
    $$bindings.breadcrumbs(breadcrumbs);
  return `<div class="text-sm breadcrumbs text-primary-content mr-auto pt-3 pl-3"><ul>${each(breadcrumbs, (breadcrumb) => {
    return `<li><a${add_attribute("href", breadcrumb.path, 0)}>${escape(breadcrumb.name)}</a></li>`;
  })}</ul></div>`;
});
const MainContent = create_ssr_component(($$result, $$props, $$bindings, slots) => {
  let { breadcrumbs } = $$props;
  let { sidebar } = $$props;
  if ($$props.breadcrumbs === void 0 && $$bindings.breadcrumbs && breadcrumbs !== void 0)
    $$bindings.breadcrumbs(breadcrumbs);
  if ($$props.sidebar === void 0 && $$bindings.sidebar && sidebar !== void 0)
    $$bindings.sidebar(sidebar);
  return `<div class="drawer drawer-open absolute top-0 bottom-0"><input id="my-drawer-2" type="checkbox" class="drawer-toggle"> <div class="drawer-content flex flex-col w-full h-full bg-white">${validate_component(Breadcrumbs, "Breadcrumbs").$$render($$result, { breadcrumbs }, {}, {})} <div class="p-3">${slots.default ? slots.default({}) : ``}</div></div> <div class="drawer-side h-full"><label for="my-drawer-2" class="drawer-overlay"></label> <ul class="hidden lg:block menu p-4 w-80 h-full bg-base-200 text-base-content">${each(sidebar, (item) => {
    return `<li><a${add_attribute("href", item.path, 0)}>${escape(item.name)}</a> ${item.subPaths.length > 0 ? `<ul>${each(item.subPaths, (subPath) => {
      return `<li><a${add_attribute("href", subPath.path, 0)}>${escape(subPath.name)}</a></li>`;
    })} </ul>` : ``} </li>`;
  })}</ul>  <ul class="lg:hidden menu p-4 w-20 h-full bg-base-200 text-base-content" data-svelte-h="svelte-1e6mge2"> <li><a>Small 1</a></li> <li><a>Small 2</a></li></ul></div></div>`;
});
const TopNav = create_ssr_component(($$result, $$props, $$bindings, slots) => {
  return `<div class="navbar bg-base-100" data-svelte-h="svelte-1egk05d"><a href="/" class="btn btn-ghost normal-case text-xl">Dromin ERP</a></div>`;
});
const SLASH = "/";
const HOME = {
  name: "Home",
  path: "/"
};
const splitPaths = (path) => {
  let retArr = [];
  if (path === SLASH)
    return retArr;
  let nextInd = 1;
  let foundInd = path.indexOf(SLASH, nextInd);
  while (foundInd > 0) {
    retArr.push(path.substring(0, foundInd));
    nextInd = foundInd + 1;
    foundInd = path.indexOf(SLASH, nextInd);
  }
  retArr.push(path);
  return retArr;
};
const getPathName = (path) => {
  return path.substring(path.lastIndexOf(SLASH) + 1);
};
function calculateBreadcrumbs(path) {
  const array = [HOME];
  let paths = splitPaths(path);
  paths.forEach((p) => array.push({ name: getPathName(p), path: p }));
  return array;
}
const Layout = create_ssr_component(($$result, $$props, $$bindings, slots) => {
  let $page, $$unsubscribe_page;
  $$unsubscribe_page = subscribe(page, (value) => $page = value);
  let { data } = $$props;
  if ($$props.data === void 0 && $$bindings.data && data !== void 0)
    $$bindings.data(data);
  $$unsubscribe_page();
  return `<main class="flex flex-col min-h-screen justify-between">${validate_component(TopNav, "TopNav").$$render($$result, {}, {}, {})} <div class="relative grow">${validate_component(MainContent, "MainContent").$$render(
    $$result,
    {
      breadcrumbs: calculateBreadcrumbs($page.url.pathname),
      sidebar: data.paths
    },
    {},
    {
      default: () => {
        return `${slots.default ? slots.default({}) : ``}`;
      }
    }
  )}</div></main>`;
});
export {
  Layout as default
};
