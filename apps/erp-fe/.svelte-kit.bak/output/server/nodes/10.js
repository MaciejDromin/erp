import * as server from '../entries/pages/inventory/categories/_page.server.ts.js';

export const index = 10;
export const component = async () => (await import('../entries/pages/inventory/categories/_page.svelte.js')).default;
export { server };
export const server_id = "src/routes/inventory/categories/+page.server.ts";
export const imports = ["_app/immutable/nodes/10.e3fb8079.js","_app/immutable/chunks/scheduler.f526bf47.js","_app/immutable/chunks/index.69537f49.js","_app/immutable/chunks/Pageable.86c16a16.js","_app/immutable/chunks/CategoryTable.559d651c.js","_app/immutable/chunks/each.e59479a4.js","_app/immutable/chunks/index.0e775aa0.js"];
export const stylesheets = ["_app/immutable/assets/Pageable.cef6e997.css"];
export const fonts = [];
