import { RECOGNIZED_PATHS } from "$lib/paths/RecognizedPaths"
import type { SidebarItem } from "$lib/types/types"

export default function(registeredItems:any[]):SidebarItem[] {
    const retArr:SidebarItem[] = []
    registeredItems.forEach(registeredItem => {
        if (RECOGNIZED_PATHS[registeredItem.name] !== undefined) {
            retArr.push(RECOGNIZED_PATHS[registeredItem.name])
        }
    })
    return retArr
}