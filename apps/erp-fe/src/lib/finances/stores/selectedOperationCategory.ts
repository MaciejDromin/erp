import { writable } from 'svelte/store'

let storeInit: string[] = []

export const operationCategoriesStore = writable(storeInit)
