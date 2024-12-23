const maintenanceKeys = {
  id: {
    data: ['maintenance', 'id'],
    form: ['id', 'val'],
  },
  date: {
    data: ['maintenance', 'date'],
    form: ['date', 'val'],
  },
  odometer: {
    data: ['maintenance', 'odometer'],
    form: ['odometer', 'val'],
  },
  contractor: {
    data: ['maintenance', 'contractorId'],
    form: ['contractor', 'val'],
  },
  parts: {
    data: ['maintenance', 'parts'],
    form: ['parts', 'val'],
  },
  partsData: {
    data: ['parts'],
    form: ['partsData'],
  },
}

export default maintenanceKeys
