package com.medilabo.notes_service.service;

import com.medilabo.notes_service.model.Note;

import java.util.List;

public interface NoteService {

    List<Note> getNotes();

    Note getNoteById(String id);

    List<Note> getNotesByPatient(String patientId);

    Note save(Note note);

    Note update(Note note);

    void deleteNote(String id);

    void deleteByPatient(String patientId);
}
