const pipe =
  (...fns) =>
  (x) =>
    fns.reduce((v, f) => f(v), x)

const lb3n16 = (val) => {
  const len = val.length
  if (len < 3 || len > 16) {
    throw new Error(`Variable length must be between 3 and 16`)
  }
  return val
}

const nonEmpty = (val) => {
  if (!val) {
    throw new Error(`Variable cannot be empty`)
  }
  if (val.length === 0) {
    throw new Error(`Variable must be longer than 0`)
  }
  return val
}

const validate = (val, ...fn) => {
  try {
    pipe(...fn)(val)
  } catch (e) {
    return { result: false, message: e.message }
  }
  return { result: true, message: '' }
}

export { validate, nonEmpty, lb3n16 }
