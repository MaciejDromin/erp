import type { SidebarItem } from "../types/types"

const RECOGNIZED_PATHS:{ [key:string]:SidebarItem } = {
    "inventory": {
        name: "Inventory",
        path: "/inventory",
        subPaths: [
            {
                name: "Category",
                path: "/inventory/categories",
                subPaths: []
            },
            {
                name: "Item",
                path: "/inventory/items",
                subPaths: []
            }
        ]
    },
    "finances": {
        name: "Finances",
        path: "/finances",
        subPaths: [
            {
                name: "Currencies",
                path: "/finances/currencies",
                subPaths: []
            },
            {
                name: "Money Operation",
                path: "/finances/money-operation",
                subPaths: []
            },
            {
                name: "Periodical Money Operations",
                path: "/finances/money-operation/periodical",
                subPaths: []
            },
            {
                name: "Object Value",
                path: "/finances/object-value",
                subPaths: []
            },
            {
                name: "Operation Category",
                path: "/finances/operation-category",
                subPaths: []
            },
            {
                name: "Planned Expenses",
                path: "/finances/planned-expenses",
                subPaths: []
            }
        ]
    }
}

export { RECOGNIZED_PATHS }