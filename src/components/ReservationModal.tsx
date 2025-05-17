
import React, { useEffect } from "react";
import { Dialog, DialogContent, DialogDescription, DialogFooter, DialogHeader, DialogTitle } from "./ui/dialog";
import { Button } from "./ui/button";
import { useForm, FormProvider } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { Reservation } from "@/types/scheduler";
import { useScheduler } from "@/contexts/SchedulerContext";
import { reservationSchema, ReservationFormValues, DEFAULT_FORM_VALUES } from "@/schemas/reservationSchema";
import ReservationForm from "./ReservationForm";

interface ReservationModalProps {
  isOpen: boolean;
  onClose: () => void;
  reservation?: Reservation;
  isEdit?: boolean;
}

const ReservationModal: React.FC<ReservationModalProps> = ({
  isOpen,
  onClose,
  reservation,
  isEdit = false,
}) => {
  const { addReservation, updateReservation } = useScheduler();
  const methods = useForm<ReservationFormValues>({
    resolver: zodResolver(reservationSchema),
    defaultValues: DEFAULT_FORM_VALUES,
  });
  
  const { reset, handleSubmit } = methods;

  useEffect(() => {
    if (reservation) {
      reset({
        facility: reservation.facility,
        month: reservation.month,
        day: reservation.day,
        timeSlot: reservation.timeSlot,
        reservationType: reservation.reservationType,
        bookedBy: reservation.comments.bookedBy,
        eventDescription: reservation.comments.eventDescription,
        additionalDetails: reservation.comments.additionalDetails || "",
      });
    } else {
      reset(DEFAULT_FORM_VALUES);
    }
  }, [reservation, reset]);

  const onSubmit = (data: ReservationFormValues) => {
    const reservationData = {
      facility: data.facility as any,
      month: data.month as any,
      day: data.day as any,
      timeSlot: data.timeSlot as any,
      reservationType: data.reservationType as any,
      comments: {
        bookedBy: data.bookedBy,
        eventDescription: data.eventDescription,
        additionalDetails: data.additionalDetails || "",
      },
    };

    if (isEdit && reservation) {
      updateReservation(reservation.id, reservationData);
    } else {
      addReservation(reservationData);
    }

    onClose();
    reset(DEFAULT_FORM_VALUES);
  };

  return (
    <Dialog open={isOpen} onOpenChange={(open) => !open && onClose()}>
      <DialogContent className="sm:max-w-md md:max-w-lg">
        <DialogHeader>
          <DialogTitle>{isEdit ? "Editar Reserva" : "Nova Reserva"}</DialogTitle>
          <DialogDescription>
            {isEdit 
              ? "Atualize os detalhes desta reserva." 
              : "Preencha os detalhes para a nova reserva."
            }
          </DialogDescription>
        </DialogHeader>
        <FormProvider {...methods}>
          <form onSubmit={handleSubmit(onSubmit)}>
            <ReservationForm />
            
            <DialogFooter className="gap-2 sm:gap-0 mt-6">
              <Button variant="outline" type="button" onClick={onClose}>
                Cancelar
              </Button>
              <Button type="submit">
                {isEdit ? "Salvar Alterações" : "Criar Reserva"}
              </Button>
            </DialogFooter>
          </form>
        </FormProvider>
      </DialogContent>
    </Dialog>
  );
};

export default ReservationModal;
