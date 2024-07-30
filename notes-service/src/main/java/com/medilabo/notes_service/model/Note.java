package com.medilabo.notes_service.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "notes")
public class Note {
    @Id
    private String id;
    private String patientId;
    private String patientName;
    private String note;
    private LocalDateTime date = LocalDateTime.now();
}
