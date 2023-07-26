import * as server from '../entries/pages/inventory/items/_page.server.ts.js';

export const index = 11;
export const component = async () => (await import('../entries/pages/inventory/items/_page.svelte.js')).default;
export { server };
export const server_id = "src/routes/inventory/items/+page.server.ts";
export const imports = ["_app/immutable/nodes/11.43a17c56.js","_app/immutable/chunks/scheduler.f526bf47.js","_app/immutable/chunks/index.69537f49.js","_app/immutable/chunks/each.e59479a4.js","_app/immutable/chunks/CategoryTable.559d651c.js","_app/immutable/chunks/index.0e775aa0.js","_app/immutable/chunks/ItemTable.404bf38a.js","_app/immutable/chunks/Modal.aaaa625c.js","_app/immutable/chunks/Pageable.86c16a16.js"];
export const stylesheets = ["_app/immutable/assets/Pageable.cef6e997.css"];
export const fonts = [];
