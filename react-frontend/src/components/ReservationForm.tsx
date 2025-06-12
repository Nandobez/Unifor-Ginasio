
import React from "react";
import { Label } from "./ui/label";
import { Input } from "./ui/input";
import { Textarea } from "./ui/textarea";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "./ui/select";
import { Controller, useFormContext } from "react-hook-form";
import { DAYS_OF_WEEK, MONTHS, TIME_SLOTS, formatTimeSlot } from "@/utils/date-utils";
import { FacilityType } from "@/types/scheduler";
import { dayTranslations, monthTranslations } from "@/utils/translations";

const ReservationForm: React.FC = () => {
  const { control, formState: { errors } } = useFormContext();
  
  const facilities: FacilityType[] = ["Quadra A", "Quadra B", "Quadra C", "Ginásio", "Arena"];

  return (
    <div className="space-y-4">
      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
        <div className="space-y-2">
          <Label htmlFor="facility">Local</Label>
          <Controller
            name="facility"
            control={control}
            render={({ field }) => (
              <Select onValueChange={field.onChange} value={field.value}>
                <SelectTrigger>
                  <SelectValue placeholder="Selecione o Local" />
                </SelectTrigger>
                <SelectContent>
                  {facilities.map((facility) => (
                    <SelectItem key={facility} value={facility}>
                      {facility}
                    </SelectItem>
                  ))}
                </SelectContent>
              </Select>
            )}
          />
          {errors.facility && <p className="text-red-500 text-sm">{errors.facility.message as string}</p>}
        </div>

        <div className="space-y-2">
          <Label htmlFor="month">Mês</Label>
          <Controller
            name="month"
            control={control}
            render={({ field }) => (
              <Select onValueChange={field.onChange} value={field.value}>
                <SelectTrigger>
                  <SelectValue placeholder="Selecione o Mês" />
                </SelectTrigger>
                <SelectContent>
                  {MONTHS.map((month) => (
                    <SelectItem key={month} value={month}>
                      {monthTranslations[month]}
                    </SelectItem>
                  ))}
                </SelectContent>
              </Select>
            )}
          />
          {errors.month && <p className="text-red-500 text-sm">{errors.month.message as string}</p>}
        </div>

        <div className="space-y-2">
          <Label htmlFor="day">Dia da Semana</Label>
          <Controller
            name="day"
            control={control}
            render={({ field }) => (
              <Select onValueChange={field.onChange} value={field.value}>
                <SelectTrigger>
                  <SelectValue placeholder="Selecione o Dia" />
                </SelectTrigger>
                <SelectContent>
                  {DAYS_OF_WEEK.map((day) => (
                    <SelectItem key={day} value={day}>
                      {dayTranslations[day]}
                    </SelectItem>
                  ))}
                </SelectContent>
              </Select>
            )}
          />
          {errors.day && <p className="text-red-500 text-sm">{errors.day.message as string}</p>}
        </div>

        <div className="space-y-2">
          <Label htmlFor="timeSlot">Horário</Label>
          <Controller
            name="timeSlot"
            control={control}
            render={({ field }) => (
              <Select onValueChange={field.onChange} value={field.value}>
                <SelectTrigger>
                  <SelectValue placeholder="Selecione o Horário" />
                </SelectTrigger>
                <SelectContent>
                  {TIME_SLOTS.map((slot) => (
                    <SelectItem key={slot} value={slot}>
                      {slot} ({formatTimeSlot(slot)})
                    </SelectItem>
                  ))}
                </SelectContent>
              </Select>
            )}
          />
          {errors.timeSlot && <p className="text-red-500 text-sm">{errors.timeSlot.message as string}</p>}
        </div>

        <div className="space-y-2">
          <Label htmlFor="reservationType">Tipo de Reserva</Label>
          <Controller
            name="reservationType"
            control={control}
            render={({ field }) => (
              <Select onValueChange={field.onChange} value={field.value}>
                <SelectTrigger>
                  <SelectValue placeholder="Selecione o Tipo" />
                </SelectTrigger>
                <SelectContent>
                  <SelectItem value="fixed">Fixo</SelectItem>
                  <SelectItem value="mobile">Móvel</SelectItem>
                </SelectContent>
              </Select>
            )}
          />
          {errors.reservationType && <p className="text-red-500 text-sm">{errors.reservationType.message as string}</p>}
        </div>

        <div className="space-y-2">
          <Label htmlFor="bookedBy">Responsável</Label>
          <Controller
            name="bookedBy"
            control={control}
            render={({ field }) => <Input {...field} placeholder="Nome da pessoa responsável" />}
          />
          {errors.bookedBy && <p className="text-red-500 text-sm">{errors.bookedBy.message as string}</p>}
        </div>
      </div>

      <div className="space-y-2">
        <Label htmlFor="eventDescription">Descrição do Evento</Label>
        <Controller
          name="eventDescription"
          control={control}
          render={({ field }) => <Input {...field} placeholder="Breve descrição do evento" />}
        />
        {errors.eventDescription && <p className="text-red-500 text-sm">{errors.eventDescription.message as string}</p>}
      </div>

      <div className="space-y-2">
        <Label htmlFor="additionalDetails">Detalhes Adicionais (Opcional)</Label>
        <Controller
          name="additionalDetails"
          control={control}
          render={({ field }) => (
            <Textarea {...field} placeholder="Contato, requisitos especiais, etc." className="min-h-[80px]" />
          )}
        />
      </div>
    </div>
  );
};

export default ReservationForm;
