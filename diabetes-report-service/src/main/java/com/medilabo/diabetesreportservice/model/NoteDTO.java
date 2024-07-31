package com.medilabo.diabetesreportservice.model;

import java.time.LocalDateTime;

public record NoteDTO(
        String id,
        String patientId,
        String patientName,
        String note,
        LocalDateTime date
) {}
