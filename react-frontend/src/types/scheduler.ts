export type FacilityType = 'Quadra A' | 'Quadra B' | 'Quadra C' | 'Ginásio' | 'Arena';

export type TimeSlot = 
  // Manhã
  | 'A-M' | 'B-M' | 'C-M' | 'D-M' | 'E-M' | 'F-M'
  // Tarde
  | 'A-T' | 'B-T' | 'C-T' | 'D-T' | 'E-T' | 'F-T'
  // Noite
  | 'A-N' | 'B-N';

export type TimeSlotMap = {
  // Manhã
  'A-M': string;
  'B-M': string;
  'C-M': string;
  'D-M': string;
  'E-M': string;
  'F-M': string;
  // Tarde
  'A-T': string;
  'B-T': string;
  'C-T': string;
  'D-T': string;
  'E-T': string;
  'F-T': string;
  // Noite
  'A-N': string;
  'B-N': string;
};

export type DayOfWeek = 'Monday' | 'Tuesday' | 'Wednesday' | 'Thursday' | 'Friday' | 'Saturday' | 'Sunday';

export type Month = 'January' | 'February' | 'March' | 'April' | 'May' | 'June' | 
                    'July' | 'August' | 'September' | 'October' | 'November' | 'December';

export type ReservationType = 'fixed' | 'mobile';

export interface Reservation {
  id: string;
  facility: FacilityType;
  month: Month;
  day: DayOfWeek;
  timeSlot: TimeSlot;
  reservationType: ReservationType;
  comments: {
    bookedBy: string;
    eventDescription: string;
    additionalDetails?: string;
  };
}
