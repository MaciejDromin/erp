import { writable } from 'svelte/store';

let storeInit:any = {}

export const selectedLabours = writable(storeInit);