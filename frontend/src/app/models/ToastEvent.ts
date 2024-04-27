import { EventTypes } from './EventTypes';

export interface ToastEvent {
  type: EventTypes;
  title: string;
  message: string;
}
