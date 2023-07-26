import { writable } from 'svelte/store';

let storeInit:string[] = []

export const categoriesStore = writable(storeInit);