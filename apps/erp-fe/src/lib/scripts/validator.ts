const pipe =
  (...fns) =>
  (x) =>
    fns.reduce((v, f) => f(v), x)

const emailRegex = new RegExp(
  /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|.(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
)
const websiteRegex = new RegExp(
  /[-a-zA-Z0-9@:%._\+~#=]{1,256}\.[a-zA-Z0-9()]{1,6}\b([-a-zA-Z0-9()@:%_\+.~#?&//=]*)/
)

const matchesRegex = (r, m) => (v) => {
  if (!r.test(v)) {
    throw new Error(
      `Field value does not match regular expression. Value should be a valid ${m}`
    )
  }
  return v
}

const isDate = (v) => {
  if (new Date(v).toString() === 'Invalid Date') {
    throw new Error(`Value ${v} is not a valid date: YYYY-MM-dd`)
  }
  return v
}

const isNbXnY = (x, y) => (v) => {
  let toValidate = Number(v)
  if (toValidate < x || toValidate > y) {
    throw new Error(`Number must be between ${x} and ${y}`)
  }
  return v
}

const isPositive = (val) => {
  if (Number(val) <= 0) {
    throw new Error(`Number must be positive`)
  }
  return val
}

const isNonNegative = (val) => {
  if (Number(val) < 0) {
    throw new Error(`Number must be non-negative`)
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
  if (val === undefined || val === null) {
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

export {
  validate,
  nonEmpty,
  lbXnY,
  leX,
  isNumber,
  isNonNegative,
  isNbXnY,
  matchesRegex,
  emailRegex,
  websiteRegex,
  isDate,
  isPositive,
}
