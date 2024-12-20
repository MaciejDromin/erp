const idObjWrapper = (val) => {
  if (typeof val === 'object' && !Array.isArray(val) && val !== null) {
    return val
  }
  return { id: val }
}

export { idObjWrapper }
