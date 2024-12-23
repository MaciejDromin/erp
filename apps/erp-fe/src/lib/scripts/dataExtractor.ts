const actualValueWrapper = (v) => {
  return v
}

const extractActualValue = (obj, keys) => {
  let curr = obj
  for (const key of keys) {
    curr = curr[key]
  }
  return curr
}

const extractValue = (
  data,
  form,
  keys,
  defaultValue = undefined,
  valueWrapperFunc = actualValueWrapper
) => {
  if (form !== undefined && form !== null && form[keys.form[0]] !== undefined) {
    return valueWrapperFunc(extractActualValue(form, keys.form))
  }
  if (data !== undefined && data !== null && data[keys.data[0]] !== undefined) {
    return valueWrapperFunc(extractActualValue(data, keys.data))
  }
  return defaultValue
}

export { extractValue }
