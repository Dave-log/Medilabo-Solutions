package com.medilabo.diabetesreportservice.service;

import com.medilabo.diabetesreportservice.constants.TriggerConstants;
import com.medilabo.diabetesreportservice.model.NoteDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TriggerCounter {

    public static Map<String, Integer> parseNotes(List<NoteDTO> notes) {
        Map<String, Integer> triggerCount = new HashMap<>();

        for (String trigger : TriggerConstants.TRIGGERS) {
            triggerCount.put(trigger.toLowerCase(), 0);
        }

        for (NoteDTO note : notes) {
            String noteContent = note.note().toLowerCase();

            for (String trigger : TriggerConstants.TRIGGERS) {
                Pattern pattern = Pattern.compile(Pattern.quote(trigger.toLowerCase()), Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(noteContent);

                if (matcher.find()) {
                    triggerCount.put(trigger.toLowerCase(), triggerCount.get(trigger.toLowerCase()) + 1);
                }
            }
        }
        return triggerCount;
    }

}
