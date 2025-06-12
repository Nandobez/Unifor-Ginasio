package com.ginasiouniforagenda.AgendamentoWeb.domain.event;

import java.time.LocalDateTime;
import java.util.UUID;

public record AgendamentoResponseDTO(
        UUID id,
        String place,
        LocalDateTime dateTime,
        String dayOfWeek,
        String timeSlot,
        String reservationType,
        String bookedBy,
        String eventDescription,
        String additionalDetails
) {
    public AgendamentoResponseDTO(Agendamento agendamento) {
        this(
                agendamento.getId(),
                agendamento.getPlace(),
                agendamento.getDateTime(),
                agendamento.getDayOfWeek(),
                agendamento.getTimeSlot(),
                agendamento.getReservationType(),
                agendamento.getBookedBy(),
                agendamento.getEventDescription(),
                agendamento.getAdditionalDetails()
        );
    }
}