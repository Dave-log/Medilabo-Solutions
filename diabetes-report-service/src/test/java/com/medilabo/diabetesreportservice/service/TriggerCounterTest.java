package com.medilabo.diabetesreportservice.service;

import com.medilabo.diabetesreportservice.model.NoteDTO;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TriggerCounterTest {

    @Test
    public void testCountTriggers_NoTriggers() {
        List<NoteDTO> notes = List.of(
                NoteDTO.builder()
                        .id("1")
                        .note("note1")
                        .build()
        );

        int result = TriggerCounter.countTriggers(notes);
        assertEquals(0, result);
    }

    @Test
    public void testCountTriggers_UniqueTriggers() {
        List<NoteDTO> notes = List.of(
                NoteDTO.builder()
                        .id("1")
                        .note("Hémoglobine A1C, Microalbumine.")
                        .build(),
                NoteDTO.builder()
                        .id("2")
                        .note("Taille, Cholestérol")
                        .build()
        );

        int result = TriggerCounter.countTriggers(notes);
        assertEquals(4, result);
    }

    @Test
    public void testCountTriggers_RepeatedTriggers() {
        List<NoteDTO> notes = List.of(
                NoteDTO.builder()
                        .id("1")
                        .note("Hémoglobine A1C.")
                        .build(),
                NoteDTO.builder()
                        .id("2")
                        .note("Hémoglobine A1C.")
                        .build(),
                NoteDTO.builder()
                        .id("3")
                        .note("Hémoglobine A1C.")
                        .build()
        );

        int result = TriggerCounter.countTriggers(notes);
        assertEquals(3, result);
    }

    @Test
    public void testCountTriggers_CaseInsensitivity() {
        List<NoteDTO> notes = List.of(
                NoteDTO.builder()
                        .id("1")
                        .note("microalbumine")
                        .build(),
                NoteDTO.builder()
                        .id("2")
                        .note("CHOLESTÉROL")
                        .build(),
                NoteDTO.builder()
                        .id("3")
                        .note("poidS, tAiLle.")
                        .build()
        );

        int result = TriggerCounter.countTriggers(notes);
        assertEquals(4, result);
    }

    @Test
    public void testCountTriggers_EmptyNotes() {
        List<NoteDTO> notes = List.of(
                NoteDTO.builder()
                        .id("1")
                        .note("")
                        .build(),
                NoteDTO.builder()
                        .id("2")
                        .note("")
                        .build()
        );

        int result = TriggerCounter.countTriggers(notes);
        assertEquals(0, result);
    }

    @Test
    public void testCountTriggers_EmptyNoteList() {
        List<NoteDTO> notes = List.of();

        int result = TriggerCounter.countTriggers(notes);
        assertEquals(0, result);
    }

}
