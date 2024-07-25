import { writable } from 'svelte/store'

let storeInit: string[] = []

export const vehiclesStore = writable(storeInit)
