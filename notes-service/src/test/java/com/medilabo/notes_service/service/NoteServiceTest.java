package com.medilabo.notes_service.service;

import com.medilabo.notes_service.exception.NoteNotFound;
import com.medilabo.notes_service.model.Note;
import com.medilabo.notes_service.repository.NoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteServiceImpl noteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetNotes() {
        List<Note> mockNotes = List.of(new Note(), new Note());
        when(noteRepository.findAll()).thenReturn(mockNotes);

        List<Note> notes = noteService.getNotes();
        assertNotNull(notes);
        assertEquals(2, notes.size());
        verify(noteRepository, times(1)).findAll();
    }

    @Test
    void testGetNoteById_Success() {
        Note mockNote = new Note();
        mockNote.setId("1");
        when(noteRepository.findById("1")).thenReturn(Optional.of(mockNote));

        Note note = noteService.getNoteById("1");
        assertNotNull(note);
        assertEquals("1", note.getId());
        verify(noteRepository, times(1)).findById("1");
    }

    @Test
    void testGetNoteById_NotFound() {
        when(noteRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(NoteNotFound.class, () -> noteService.getNoteById("1"));
        verify(noteRepository, times(1)).findById("1");
    }

    @Test
    void testGetNotesByPatient() {
        List<Note> mockNotes = List.of(new Note(), new Note());
        when(noteRepository.findByPatientId("patient1")).thenReturn(mockNotes);

        List<Note> notes = noteService.getNotesByPatient("patient1");
        assertNotNull(notes);
        assertEquals(2, notes.size());
        verify(noteRepository, times(1)).findByPatientId("patient1");
    }

    @Test
    void testSaveNote() {
        Note newNote = new Note();
        newNote.setId("123");
        newNote.setPatientName("John Doe");
        newNote.setNote("Sample note");

        when(noteRepository.save(any(Note.class))).thenReturn(newNote);

        Note savedNote = noteService.save(newNote);
        assertNotNull(savedNote);
        assertEquals("123", savedNote.getId());
        assertEquals("John Doe", savedNote.getPatientName());
        verify(noteRepository, times(1)).save(newNote);
    }

    @Test
    void testUpdateNote_Success() {
        Note existingNote = new Note();
        existingNote.setId("1");
        existingNote.setPatientName("Jane Doe");
        existingNote.setNote("Old note");

        Note updatedNote = new Note();
        updatedNote.setId("1");
        updatedNote.setPatientName("Jane Doe");
        updatedNote.setNote("Updated note");

        when(noteRepository.findById("1")).thenReturn(Optional.of(existingNote));
        when(noteRepository.save(any(Note.class))).thenReturn(updatedNote);

        Note result = noteService.update(updatedNote);
        assertNotNull(result);
        assertEquals("Updated note", result.getNote());
        verify(noteRepository, times(1)).findById("1");
        verify(noteRepository, times(1)).save(existingNote);
    }

    @Test
    void testUpdateNote_NotFound() {
        Note updatedNote = new Note();
        updatedNote.setId("1");
        updatedNote.setPatientName("Jane Doe");
        updatedNote.setNote("Updated note");

        when(noteRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(NoteNotFound.class, () -> noteService.update(updatedNote));
        verify(noteRepository, times(1)).findById("1");
        verify(noteRepository, times(0)).save(any(Note.class));
    }

    @Test
    void testDeleteNote() {
        String noteId = "1";
        doNothing().when(noteRepository).deleteById(noteId);

        noteService.deleteNote(noteId);
        verify(noteRepository, times(1)).deleteById(noteId);
    }

    @Test
    void testDeleteByPatient() {
        String patientId = "patient1";
        doNothing().when(noteRepository).deleteByPatientId(patientId);

        noteService.deleteByPatient(patientId);
        verify(noteRepository, times(1)).deleteByPatientId(patientId);
    }
}
