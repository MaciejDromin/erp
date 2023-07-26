import { c as create_ssr_component, b as escape, a as add_attribute } from "./ssr.js";
const Modal = create_ssr_component(($$result, $$props, $$bindings, slots) => {
  let { modalId } = $$props;
  let { buttonName } = $$props;
  if ($$props.modalId === void 0 && $$bindings.modalId && modalId !== void 0)
    $$bindings.modalId(modalId);
  if ($$props.buttonName === void 0 && $$bindings.buttonName && buttonName !== void 0)
    $$bindings.buttonName(buttonName);
  return `<div> <button class="btn">${escape(buttonName)}</button> <dialog${add_attribute("id", modalId, 0)} class="modal"><form method="dialog" class="modal-box bg-white px-12 py-6">${``}</form></dialog></div>`;
});
export {
  Modal as M
};
