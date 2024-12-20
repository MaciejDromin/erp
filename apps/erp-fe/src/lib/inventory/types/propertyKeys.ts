const propertyKeys = {
  id: {
    data: ['property', 'id'],
    form: ['id', 'val'],
  },
  name: {
    data: ['property', 'name'],
    form: ['name', 'val'],
  },
  uniqueIdentifier: {
    data: ['property', 'uniqueIdentifier'],
    form: ['uniqueIdentifier', 'val'],
  },
  landRegister: {
    data: ['property', 'landRegister'],
    form: ['landRegister', 'val'],
  },
  propertyType: {
    data: ['property', 'propertyInformation', 'propertyType'],
    form: ['propertyInformation', 'propertyType', 'val'],
  },
  landClassification: {
    data: ['property', 'propertyInformation', 'landClassification'],
    form: ['propertyInformation', 'landClassification', 'val'],
  },
  area: {
    data: ['property', 'propertyInformation', 'landArea', 'area'],
    form: ['propertyInformation', 'landArea', 'area', 'val'],
  },
  unit: {
    data: ['property', 'propertyInformation', 'landArea', 'unit'],
    form: ['propertyInformation', 'landArea', 'unit', 'val'],
  },
  address: {
    data: ['property', 'addressId'],
    form: ['address', 'val'],
  },
}

export default propertyKeys
