package com.ginasiouniforagenda.AgendamentoWeb.domain.event;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "agendamentos")
public class Agendamento {
    @Id
    @GeneratedValue
    private UUID id;

    private String place;
    private LocalDateTime dateTime;
    private String dayOfWeek;
    private String timeSlot;
    private String reservationType;
    private String bookedBy;
    private String eventDescription;
    private String additionalDetails;

    public Agendamento() {}

    public Agendamento(AgendamentoRequestDTO dto) {
        this.place = dto.place();
        this.dateTime = dto.dateTime();
        this.dayOfWeek = dto.dayOfWeek();
        this.timeSlot = dto.timeSlot();
        this.reservationType = dto.reservationType();
        this.bookedBy = dto.bookedBy();
        this.eventDescription = dto.eventDescription();
        this.additionalDetails = dto.additionalDetails();
    }
}