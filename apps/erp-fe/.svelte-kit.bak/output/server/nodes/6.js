import * as server from '../entries/pages/finances/object-value/_page.server.ts.js';

export const index = 6;
export const component = async () => (await import('../entries/pages/finances/object-value/_page.svelte.js')).default;
export { server };
export const server_id = "src/routes/finances/object-value/+page.server.ts";
export const imports = ["_app/immutable/nodes/6.584dc590.js","_app/immutable/chunks/scheduler.f526bf47.js","_app/immutable/chunks/index.69537f49.js","_app/immutable/chunks/each.e59479a4.js","_app/immutable/chunks/ItemTable.404bf38a.js","_app/immutable/chunks/index.0e775aa0.js","_app/immutable/chunks/Modal.aaaa625c.js","_app/immutable/chunks/Pageable.86c16a16.js"];
export const stylesheets = ["_app/immutable/assets/Pageable.cef6e997.css"];
export const fonts = [];
