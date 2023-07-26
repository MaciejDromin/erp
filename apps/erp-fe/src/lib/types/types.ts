interface Breadcrumb {
    name:string,
    path:string
}

interface SidebarItem {
    name:string,
    path:string,
    subPaths:SidebarItem[]
}

export type { Breadcrumb, SidebarItem }