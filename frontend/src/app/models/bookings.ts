import { Address } from "./Address";
import { BaseEntity } from "./baseEntity";
import { Car } from "./car";
import { CarDealer } from "./carDealer";
import { User } from "./user";

export enum StatusType{
  paid, unpaid, reserved, cancelled
}
export interface Bookings extends BaseEntity{
    id?: number;
    fromDate: Date |string;
    toDate: Date | string;
    status: StatusType;
    cancellation: boolean;
    amendments: boolean;
    theftProtection: boolean;
    collisionDamage: boolean;
    fullInsurance: boolean;
    additionalDriver: boolean;
    price: number;
    cancelRequest: boolean;
    carDealer: CarDealer;
    user: User;
    car: Car;
    address: Address;
}
