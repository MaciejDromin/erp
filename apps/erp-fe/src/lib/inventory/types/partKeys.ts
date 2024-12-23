const partKeys = {
  id: {
    data: ['part', 'id'],
    form: ['id', 'val'],
  },
  name: {
    data: ['part', 'name'],
    form: ['name', 'val'],
  },
  partNumber: {
    data: ['part', 'partNumber'],
    form: ['partNumber', 'val'],
  },
  contractor: {
    data: ['part', 'manufacturerId'],
    form: ['manufacturer', 'val'],
  },
}

export default partKeys
