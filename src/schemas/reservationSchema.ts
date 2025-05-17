
import { z } from "zod";
import { FacilityType, ReservationType } from "@/types/scheduler";

export const reservationSchema = z.object({
  facility: z.string(),
  month: z.string(),
  day: z.string(),
  timeSlot: z.string(),
  reservationType: z.enum(["fixed", "mobile"]),
  bookedBy: z.string().min(1, "Nome do responsável é obrigatório"),
  eventDescription: z.string().min(1, "Descrição do evento é obrigatória"),
  additionalDetails: z.string().optional(),
});

export type ReservationFormValues = z.infer<typeof reservationSchema>;

export const DEFAULT_FORM_VALUES = {
  facility: "Quadra A" as FacilityType,
  month: "",
  day: "Monday",
  timeSlot: "A-M",
  reservationType: "mobile" as ReservationType,
  bookedBy: "",
  eventDescription: "",
  additionalDetails: "",
};
