package com.medilabo.diabetesreportservice.model;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record NoteDTO(
        String id,
        String patientId,
        String patientName,
        String note,
        LocalDateTime date
) {}
