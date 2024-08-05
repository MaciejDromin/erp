import { writable } from 'svelte/store'

let storeInit: string[] = []

export const maintenanceStore = writable(storeInit)
