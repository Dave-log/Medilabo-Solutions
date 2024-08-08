package com.medilabo.diabetesreportservice.service;

import com.medilabo.diabetesreportservice.constants.TriggerConstants;
import com.medilabo.diabetesreportservice.model.NoteDTO;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class for counting trigger words in patient notes.
 */
public final class TriggerCounter {

    /**
     * Counts the number of trigger words found in a list of patient notes.
     *
     * @param notes the list of {@link NoteDTO} objects containing the notes
     * @return the count of trigger words found across all notes
     */
    public static int countTriggers(List<NoteDTO> notes) {
        List<String> triggers = new ArrayList<>();

        for (NoteDTO note : notes) {
            String noteContent = note.note().toLowerCase();

            for (String trigger : TriggerConstants.TRIGGERS) {
                Pattern pattern = Pattern.compile(Pattern.quote(trigger.toLowerCase()), Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(noteContent);

                if (matcher.find()) {
                    triggers.add(trigger.toLowerCase());
                }
            }
        }
        return triggers.size();
    }

}
