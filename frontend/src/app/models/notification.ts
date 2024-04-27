import { BaseEntity } from "./baseEntity";
import { User } from "./user";

export interface Notification extends BaseEntity{
  id: number;
  message: string;
  isRead: boolean;
  user: User;
}
