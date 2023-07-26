import { writable } from 'svelte/store';

let storeInit:string[] = []

export const itemsStore = writable(storeInit);