const pipe =
  (...fns) =>
  (x) =>
    fns.reduce((v, f) => f(v), x)

const isNbXnY = (x, y) => (v) => {
  let toValidate = Number(v)
  if (toValidate < x || toValidate > y) {
    throw new Error(`Number must be between ${x} and ${y}`)
  }
  return v
}

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

const leX = (x) => (val) => {
  if (val.length !== x) {
    throw new Error(`Variable must be of exactly ${x} characters`)
  }
  return val
}

const lbXnY = (x, y) => (val) => {
  const len = val.length
  if (len < x || len > y) {
    throw new Error(`Variable length must be between ${x} and ${y}`)
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

export { validate, nonEmpty, lbXnY, leX, isNumber, isNonNegative, isNbXnY }
