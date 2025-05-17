
import React, { createContext, useContext, useState } from "react";
import { mockReservations } from "../data/reservations";
import { FacilityType, Month, Reservation, ReservationType, TimeSlot, DayOfWeek } from "../types/scheduler";
import { getCurrentMonth, TIME_SLOT_MAP } from "../utils/date-utils";
import { useToast } from "@/components/ui/use-toast";

interface SchedulerContextType {
  reservations: Reservation[];
  selectedFacility: FacilityType | "all";
  selectedMonth: Month;
  selectedReservationType: ReservationType | "all";
  selectedYear: number;
  selectedDay: DayOfWeek | undefined;
  setSelectedDay: (day: DayOfWeek | undefined) => void;
  setSelectedFacility: (facility: FacilityType | "all") => void;
  setSelectedMonth: (month: Month) => void;
  setSelectedReservationType: (type: ReservationType | "all") => void;
  addReservation: (reservation: Omit<Reservation, "id">) => void;
  updateReservation: (id: string, reservation: Partial<Omit<Reservation, "id">>) => void;
  deleteReservation: (id: string) => void;
  filteredReservations: Reservation[];
}

export const SchedulerContext = createContext<SchedulerContextType | undefined>(undefined);

export const useScheduler = (): SchedulerContextType => {
  const context = useContext(SchedulerContext);
  if (!context) {
    throw new Error("useScheduler must be used within a SchedulerProvider");
  }
  return context;
};

export const SchedulerProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [reservations, setReservations] = useState<Reservation[]>(mockReservations);
  const [selectedFacility, setSelectedFacility] = useState<FacilityType | "all">("all");
  const [selectedMonth, setSelectedMonth] = useState<Month>(getCurrentMonth());
  const [selectedReservationType, setSelectedReservationType] = useState<ReservationType | "all">("all");
  const [selectedYear] = useState<number>(2024);
  const [selectedDay, setSelectedDay] = useState<DayOfWeek | undefined>(undefined);
  const { toast } = useToast();

  const addReservation = (reservation: Omit<Reservation, "id">) => {
    const newReservation = {
      ...reservation,
      id: Date.now().toString(),
    };

    setReservations((prev) => [...prev, newReservation]);
    toast({
      title: "Reservation created",
      description: `${reservation.facility} - ${reservation.day} ${formatTimeSlot(reservation.timeSlot)}`,
    });
  };

  const updateReservation = (id: string, reservation: Partial<Omit<Reservation, "id">>) => {
    setReservations((prev) =>
      prev.map((item) => (item.id === id ? { ...item, ...reservation } : item))
    );
    toast({
      title: "Reservation updated",
      description: "Your changes have been saved",
    });
  };

  const deleteReservation = (id: string) => {
    setReservations((prev) => prev.filter((item) => item.id !== id));
    toast({
      title: "Reservation deleted",
      description: "The reservation has been removed",
      variant: "destructive",
    });
  };

  const formatTimeSlot = (timeSlot: TimeSlot) => {
    return TIME_SLOT_MAP[timeSlot] || timeSlot;
  };

  const filteredReservations = reservations.filter((reservation) => {
    const facilityMatch = selectedFacility === "all" || reservation.facility === selectedFacility;
    const monthMatch = reservation.month === selectedMonth;
    const typeMatch =
      selectedReservationType === "all" || reservation.reservationType === selectedReservationType;
    const dayMatch = !selectedDay || reservation.day === selectedDay;
    return facilityMatch && monthMatch && typeMatch && dayMatch;
  });

  return (
    <SchedulerContext.Provider
      value={{
        reservations,
        selectedFacility,
        selectedMonth,
        selectedReservationType,
        selectedYear,
        selectedDay,
        setSelectedDay,
        setSelectedFacility,
        setSelectedMonth,
        setSelectedReservationType,
        addReservation,
        updateReservation,
        deleteReservation,
        filteredReservations,
      }}
    >
      {children}
    </SchedulerContext.Provider>
  );
};
