const contractorKeys = {
  id: {
    data: ['contractor', 'id'],
    form: ['id', 'val'],
  },
  name: {
    data: ['contractor', 'name'],
    form: ['name', 'val'],
  },
  phoneNumber: {
    data: ['contractor', 'contactInformation', 'phoneNumber'],
    form: ['phoneNumber', 'val'],
  },
  email: {
    data: ['contractor', 'contactInformation', 'email'],
    form: ['email', 'val'],
  },
  website: {
    data: ['contractor', 'contactInformation', 'website'],
    form: ['website', 'val'],
  },
}

export default contractorKeys
