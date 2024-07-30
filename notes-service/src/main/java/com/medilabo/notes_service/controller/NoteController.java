package com.medilabo.notes_service.controller;

import com.medilabo.notes_service.model.Note;
import com.medilabo.notes_service.service.NoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public ResponseEntity<List<Note>> getAllNotes() {
        List<Note> notes = noteService.getNotes();
        return ResponseEntity.ok(notes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable String id) {
        Note note = noteService.getNoteById(id);
        return ResponseEntity.ok(note);
    }

    @GetMapping("/{patientId}")
    public ResponseEntity<List<Note>> getNoteByPatientId(@PathVariable String patientId) {
        List<Note> notes = noteService.getNotesByPatients(patientId);
        return ResponseEntity.ok(notes);
    }

    @PostMapping
    public ResponseEntity<Note> createNote(@RequestBody Note note) {
        Note savedNote = noteService.save(note);
        return ResponseEntity.ok(savedNote);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable String id, @RequestBody Note note) {
        note.setId(id);
        Note updatedNote = noteService.update(note);
        return ResponseEntity.ok(updatedNote);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable String id) {
        noteService.deleteNote(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{patientId}")
    public ResponseEntity<Void> deleteNoteByPatientId(@PathVariable String patientId) {
        noteService.deleteByPatient(patientId);
        return ResponseEntity.noContent().build();
    }
}
