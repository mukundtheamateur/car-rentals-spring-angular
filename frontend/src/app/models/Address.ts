import { BaseEntity } from "./baseEntity";

export interface Address extends BaseEntity{

  id:number;
  pickupAddress: string;
  dropAddress: string;
}
