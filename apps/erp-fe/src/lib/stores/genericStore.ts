import { writable } from 'svelte/store'

let storeInit: {} = {}

export const genericStore = writable(storeInit)
