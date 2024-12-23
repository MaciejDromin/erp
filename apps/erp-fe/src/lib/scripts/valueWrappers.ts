const idObjWrapper = (val) => {
  if (val === undefined) return undefined
  if (typeof val === 'object' && !Array.isArray(val) && val !== null) {
    return val
  }
  return { id: val }
}

const partMapper = (parts) => {
  return new Map(parts.map((p) => [p.id, p.quantity]))
}

export { idObjWrapper, partMapper }
