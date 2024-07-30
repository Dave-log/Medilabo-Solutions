package com.medilabo.notes_service.exception;

public class NoteNotFound extends RuntimeException {
    public NoteNotFound(String message) {
        super(message);
    }
}
