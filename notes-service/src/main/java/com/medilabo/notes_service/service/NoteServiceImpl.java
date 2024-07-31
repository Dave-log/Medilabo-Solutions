package com.medilabo.notes_service.service;

import com.medilabo.notes_service.exception.NoteNotFound;
import com.medilabo.notes_service.model.Note;
import com.medilabo.notes_service.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    public NoteServiceImpl(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public List<Note> getNotes() {
        return noteRepository.findAll();
    }

    @Override
    public Note getNoteById(String id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFound("Note does not exist (id provided: " + id + ")"));
    }

    @Override
    public List<Note> getNotesByPatient(String patientId) {
        return noteRepository.findByPatientId(patientId);
    }

    @Override
    public Note save(Note note) {
        return noteRepository.save(note);
    }

    @Override
    public Note update(Note note) {
        Note noteToUpdate = getNoteById(note.getId());

        noteToUpdate.setPatientName(note.getPatientName());
        noteToUpdate.setNote(note.getNote());
        noteToUpdate.setDate(note.getDate());

        return noteRepository.save(noteToUpdate);
    }

    @Override
    public void deleteNote(String id) {
        noteRepository.deleteById(id);
    }

    @Override
    public void deleteByPatient(String patientId) {
        noteRepository.deleteByPatientId(patientId);
    }
}
