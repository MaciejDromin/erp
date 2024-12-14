const pipe =
  (...fns) =>
  (x) =>
    fns.reduce((v, f) => f(v), x)

const isNonNegative = (val) => {
  if (Number(val) < 0) {
    throw new Error(`Number must be positive`)
  }
  return val
}

const isNumber = (val) => {
  if (isNaN(val)) {
    throw new Error(`Variable must be a number`)
  }
  return val
}

const le3 = (val) => {
  if (val.length !== 3) {
    throw new Error(`Variable must be of exactly 3 characters`)
  }
  return val
}

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
  return { result: true, message: undefined }
}

export { validate, nonEmpty, lb3n16, le3, isNumber, isNonNegative }
