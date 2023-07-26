import { c as create_ssr_component, e as each, b as escape, d as subscribe, v as validate_component, a as add_attribute } from "../../../../chunks/ssr.js";
import { i as itemsStore, I as ItemTable } from "../../../../chunks/ItemTable.js";
import { M as Modal } from "../../../../chunks/Modal.js";
import { P as Pageable } from "../../../../chunks/Pageable.js";
import { H as HttpMethods } from "../../../../chunks/httpMethods.js";
const ObjectValueTable = create_ssr_component(($$result, $$props, $$bindings, slots) => {
  let { data = void 0 } = $$props;
  let objectNameMap = {};
  const getObjectNames = async (dt) => {
    if (dt === void 0)
      return;
    const res = await fetch("/api/inventory/items/object-names", {
      method: HttpMethods.POST,
      body: JSON.stringify({ itemIds: extractObjectIds(dt.content) })
    });
    objectNameMap = await res.json();
  };
  const extractObjectIds = (items) => {
    let ret = [];
    items.forEach((item) => ret.push(item.objectId));
    return ret;
  };
  const getName = (nameMap, objectId) => {
    return nameMap[objectId];
  };
  if ($$props.data === void 0 && $$bindings.data && data !== void 0)
    $$bindings.data(data);
  data && getObjectNames(data);
  return `<div class="overflow-x-auto text-primary-content mx-auto"> <table class="table"> <thead class="text-primary-content" data-svelte-h="svelte-ogqtx0"><tr><th>ID</th> <th>Object Name</th> <th>Amount</th> <th>Currency</th></tr></thead> <tbody>${data !== void 0 ? `${each(data.content, (objectValue) => {
    return `<tr><td>${escape(objectValue.uuid)}</td> <td>${escape(getName(objectNameMap, objectValue.objectId))}</td> <td>${escape(objectValue.amount.value)}</td> <td>${escape(objectValue.amount.currencyCode)}</td> </tr>`;
  })}` : ``}</tbody></table></div>`;
});
const Page = create_ssr_component(($$result, $$props, $$bindings, slots) => {
  let $$unsubscribe_itemsStore;
  $$unsubscribe_itemsStore = subscribe(itemsStore, (value) => value);
  let { data } = $$props;
  let items = [];
  itemsStore.subscribe((itm) => {
    items = [...itm];
    items = items;
  });
  const determineButtonName = (arr) => {
    if (arr.length === 0)
      return "select item";
    return `${arr.length} item selected`;
  };
  if ($$props.data === void 0 && $$bindings.data && data !== void 0)
    $$bindings.data(data);
  $$unsubscribe_itemsStore();
  return `<div id="item" class="flex flex-col gap-3 px-10 pt-10"><div class="stats shadow mx-auto"><div class="stat"><div class="stat-figure text-primary text-4xl" data-svelte-h="svelte-1g0b4w2">$</div> <div class="stat-title" data-svelte-h="svelte-1aogcmk">Total Amount</div> <div class="stat-value text-primary">${escape(data.objectsValue.totalAmount.value)} ${escape(data.objectsValue.totalAmount.currencyCode)}</div> <div class="stat-desc" data-svelte-h="svelte-1g9muqb">Total amount of all objects</div></div> <div class="stat"><div class="stat-figure text-secondary text-3xl" data-svelte-h="svelte-1rbgisp">0..*</div> <div class="stat-title" data-svelte-h="svelte-15amjvl">Objects Count</div> <div class="stat-value text-secondary">${escape(data.objectsValue.objectsCount)}</div> <div class="stat-desc" data-svelte-h="svelte-1kqpz1p">How much objects were counted</div></div></div> <form method="POST" class="mx-auto flex flex-col gap-3 py-6"><div class="flex flex-row gap-3" data-svelte-h="svelte-1iesgvg"><input name="amount" type="text" placeholder="Amount" class="input input-bordered input-primary w-full max-w-xs"> <input name="currencyCode" type="text" placeholder="Currency Code" class="input input-bordered input-primary w-full max-w-xs"></div> <div class="flex flex-row gap-3"><select multiple name="itemId" class="p-4 mr-auto hidden">${each(items, (item) => {
    return `<option${add_attribute("value", item, 0)}></option>`;
  })}</select> <div class="mr-auto">${validate_component(Modal, "Modal").$$render(
    $$result,
    {
      modalId: "item_modal",
      buttonName: determineButtonName(items)
    },
    {},
    {
      default: () => {
        return `${validate_component(Pageable, "Pageable").$$render(
          $$result,
          {
            endpoint: "/inventory/items",
            component: ItemTable
          },
          {},
          {}
        )}`;
      }
    }
  )}</div> <button class="btn btn-primary" data-svelte-h="svelte-1q26zcv">Add Row</button></div></form> ${validate_component(Pageable, "Pageable").$$render(
    $$result,
    {
      endpoint: "/finances/object-value",
      component: ObjectValueTable
    },
    {},
    {}
  )}</div>`;
});
export {
  Page as default
};
