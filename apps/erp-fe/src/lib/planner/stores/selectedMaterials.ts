import { writable } from 'svelte/store'

let storeInit: any = {}

export const selectedMaterials = writable(storeInit)
