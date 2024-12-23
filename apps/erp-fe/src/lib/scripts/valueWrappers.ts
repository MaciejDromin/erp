const idObjWrapper = (val) => {
  if (val === undefined || val === null) return val
  if (typeof val === 'object' && !Array.isArray(val)) {
    return val
  }
  return { id: val }
}

const partMapper = (parts) => {
  return new Map(parts.map((p) => [p.id, p.quantity]))
}

export { idObjWrapper, partMapper }
