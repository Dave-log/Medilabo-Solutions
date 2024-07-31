package com.medilabo.diabetesreportservice.service;

import com.medilabo.diabetesreportservice.constants.TriggerConstants;
import com.medilabo.diabetesreportservice.model.NoteDTO;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class TriggerCounter {

    public static int countUniqueTriggers(List<NoteDTO> notes) {
        Set<String> uniqueTriggers = new HashSet<>();

        for (NoteDTO note : notes) {
            String noteContent = note.note().toLowerCase();

            for (String trigger : TriggerConstants.TRIGGERS) {
                Pattern pattern = Pattern.compile(Pattern.quote(trigger.toLowerCase()), Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(noteContent);

                if (matcher.find()) {
                    uniqueTriggers.add(trigger.toLowerCase());
                }
            }
        }
        return uniqueTriggers.size();
    }

}
