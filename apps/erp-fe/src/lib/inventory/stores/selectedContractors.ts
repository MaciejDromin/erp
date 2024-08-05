import { writable } from 'svelte/store'

let storeInit: string[] = []

export const contractorsStore = writable(storeInit)
