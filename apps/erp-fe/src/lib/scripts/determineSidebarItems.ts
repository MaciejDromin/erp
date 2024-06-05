import { RECOGNIZED_PATHS } from "$lib/paths/RecognizedPaths"
import type { SidebarItem } from "$lib/types/types"

export default function(registeredItems:any[]):SidebarItem[] {
    const retArr:SidebarItem[] = []
    Object.keys(registeredItems).forEach(registeredItem => {
        if (RECOGNIZED_PATHS[registeredItem] !== undefined) {
            retArr.push(RECOGNIZED_PATHS[registeredItem])
        }
    })
    return retArr
}
