import { BaseEntity } from './baseEntity';
import { CarDealer } from './carDealer';

export enum FuelType {
	Diesel,
	Petrol,
	ElectricVehicle
}

export enum GearBoxType {
	Manual,
	Automatic
}

export enum FuelPolicyType {
	AlreadyFilled,
	FreeTank
}

export enum WheelDriveType {
	FWD,
	RWD,
	FourWD,
	AWD
}

export interface Car extends BaseEntity {
    id: number;
    carName: string;
    price: number;
    deposit: number;
    fuelType: FuelType;
    gearbox: GearBoxType;
    image: string;
    seats: number;
    doors: number;
    fuelpolicy: FuelPolicyType;
    mileage: number;
    cancellation: number;
    amendments: number;
    theftProtection: number;
    collisionDamage: number;
    fullInsurance: number;
    additionalDriver: number;
    wheelDrive: WheelDriveType;
    dealer: CarDealer;
}
