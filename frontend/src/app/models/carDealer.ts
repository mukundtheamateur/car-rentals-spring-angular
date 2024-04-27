import { Roles } from "./roles";

export interface CarDealer{
    id: number;
    dealerName: string;
    email: string;
    refreshToken?: string;
    phone: string;
    verified?: boolean;
    allCities: string;
    avatar: string;
    role: Roles;
}
