enum ItemUnit {
  M3 = 'M3',
  M2 = 'M2',
  SZT = 'SZT',
  L = 'L',
}

enum PropertyType {
  LAND = 'LAND',
  HOUSE = 'HOUSE',
  BUILDING = 'BUILDING',
  APARTMENT = 'APARTMENT',
}

enum LandClassification {
  BUILDING_PLOT = 'BUILDING_PLOT',
  AGRICULTURE_PLOT = 'AGRICULTURE_PLOT',
  HABITAT_PLOT = 'HABITAT_PLOT',
  INVESTMENT_PLOT = 'INVESTMENT_PLOT',
  RECREATIONAL_PLOT = 'RECREATIONAL_PLOT',
  FORESTRY_PLOT = 'FORESTRY_PLOT',
}

enum AreaUnit {
  M2 = 'Square Meters',
  ARE = 'Are',
  HA = 'Hectare',
}

enum BodyStyle {
  STATION_WAGON = 'Station Wagon',
  SUV = 'SUV',
  COUPE = 'Coupe',
  HATCHBACK = 'Hatchback',
  PICKUP = 'Pickup',
  SEDAN = 'Sedan',
}

enum DriveTrain {
  FWD = 'Front Wheel Drive',
  RWD = 'Rear Wheel Drive',
  AWD = 'All Wheel Drive',
  AWD_FWD = 'AWD W/FWD',
  AWD_RWD = 'AWD W/RWD',
}

enum FuelType {
  GASOLINE = 'Gasoline',
  DIESEL = 'Diesel',
  LPG = 'LPG',
  HYBRID = 'Hybrid',
  ELECTRIC = 'Electric',
  HYDROGEN = 'Hydrogen',
}

enum Make {
  VOLKSWAGEN = 'Volkswagen',
  AUDI = 'Audi',
  FORD = 'Ford',
  MERCEDES_BENZ = 'Mercedes-Benz',
  OPEL = 'Opel',
  SKODA = 'Skoda',
  RENAULT = 'Renault',
  PEUGEOT = 'Peugeot',
  ALFA_ROMEO = 'Alfa Romeo',
  VOLVO = 'Volvo',
  FIAT = 'Fiat',
  TESLA = 'Tesla',
  JAGUAR = 'Jaguar',
  JEEP = 'Jeep',
  KIA = 'Kia',
  LAND_ROVER = 'Land Rover',
  LEXUS = 'Lexus',
  MAZDA = 'Mazda',
  NISSAN = 'Nissan',
  DODGE = 'Dodge',
  SUBARU = 'Subaru',
  SUZUKI = 'Suzuki',
  TOYOTA = 'Toyota',
}

enum Transmission {
  AUTOMATIC = 'Automatic',
  MANUAL = 'Manual',
}

export {
  ItemUnit,
  PropertyType,
  LandClassification,
  AreaUnit,
  BodyStyle,
  DriveTrain,
  FuelType,
  Make,
  Transmission,
}
