import type { Breadcrumb } from "../types/types"

const SLASH:string = "/"

const HOME:Breadcrumb = {
    name: "Home",
    path: "/"
}

const splitPaths = (path:string):string[] => {
    let retArr:string[] = []
    if (path === SLASH) return retArr
    let nextInd:number = 1
    let foundInd:number = path.indexOf(SLASH, nextInd)
    while (foundInd > 0) {
        retArr.push(path.substring(0, foundInd))
        nextInd = foundInd + 1
        foundInd = path.indexOf(SLASH, nextInd)
    }
    retArr.push(path)
    return retArr
}

const getPathName = (path:string):string => {
    return path.substring(path.lastIndexOf(SLASH) + 1)
}

export default function(path:string):Breadcrumb[] {
    const array:Breadcrumb[] = [HOME]
    let paths = splitPaths(path)
    paths.forEach(p => array.push({ name: getPathName(p), path: p}))
    return array
}