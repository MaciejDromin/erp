import { writable } from 'svelte/store'

let storeInit: string[] = []

export const addressesStore = writable(storeInit)
