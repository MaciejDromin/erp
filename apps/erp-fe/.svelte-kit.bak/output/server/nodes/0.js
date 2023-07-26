import * as server from '../entries/pages/_layout.server.ts.js';

export const index = 0;
export const component = async () => (await import('../entries/pages/_layout.svelte.js')).default;
export { server };
export const server_id = "src/routes/+layout.server.ts";
export const imports = ["_app/immutable/nodes/0.779bf089.js","_app/immutable/chunks/scheduler.f526bf47.js","_app/immutable/chunks/index.69537f49.js","_app/immutable/chunks/each.e59479a4.js","_app/immutable/chunks/stores.4e60f161.js","_app/immutable/chunks/singletons.4e1313c7.js","_app/immutable/chunks/index.0e775aa0.js"];
export const stylesheets = ["_app/immutable/assets/0.af9af9fc.css"];
export const fonts = [];
