package com.ginasiouniforagenda.AgendamentoWeb.domain.event;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AgendamentoRequestDTO(
        @NotBlank String place,
        @NotNull @JsonDeserialize(using = LocalDateTimeDeserializer.class) LocalDateTime dateTime,
        @NotBlank String dayOfWeek,
        @NotBlank String timeSlot,
        @NotBlank String reservationType,
        @NotBlank String bookedBy,
        String eventDescription,
        String additionalDetails
) {}