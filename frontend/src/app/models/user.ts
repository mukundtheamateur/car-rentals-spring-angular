import { Roles } from "./roles";


export interface User {
  id: number;
  email: string;
  phone: string;
  fullname: string;
  verified?: boolean;
  dateOfBirth: Date;
  enableEmailNotification?: boolean;
  avatar?: string;
  bio?: string;
  city: string;
  blacklisted?: boolean;
  payLater?: boolean;
  refreshToken?: string;
  password: string;
  role: Roles;
}
