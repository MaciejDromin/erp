import { writable } from 'svelte/store'

let storeInit: string[] = []

export const propertiesStore = writable(storeInit)
