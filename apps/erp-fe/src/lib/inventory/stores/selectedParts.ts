import { writable } from 'svelte/store'

let storeInit: string[] = []

export const partsStore = writable(storeInit)
