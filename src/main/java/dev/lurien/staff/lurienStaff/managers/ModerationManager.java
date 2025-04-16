package dev.lurien.staff.lurienStaff.managers;

import lombok.Getter;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ModerationManager {

    @Getter
    private static final List<String> badWords = new ArrayList<>();
    @Getter
    private static final List<String> bypassBadWords = new ArrayList<>();

    public static String removeDiacriticalMarks(String str) {
        String normalize = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalize).replaceAll("");
    }

    public static String changeNumbersPerChars(String str) {
        return str.replace('4', 'a').replace('3', 'e').replace('1', 'i').replace('0', 'o').replace('5', 'u');
    }

    public static boolean containsBadWord(String str) {
        String normalizedMessage = changeNumbersPerChars(removeDiacriticalMarks(str.toLowerCase()));

        for (String badWord : badWords) {
            String normalizedBadWord = changeNumbersPerChars(removeDiacriticalMarks(badWord.toLowerCase()));

            if (!normalizedMessage.contains(normalizedBadWord)) continue;

            boolean isBypassed = false;
            for (String bypass : bypassBadWords) {
                String normalizedBypass = changeNumbersPerChars(removeDiacriticalMarks(bypass.toLowerCase()));

                if (normalizedBypass.contains(normalizedBadWord) && normalizedMessage.contains(normalizedBypass)) {
                    isBypassed = true;
                    break;
                }
            }

            if (!isBypassed) {
                return true;
            }
        }

        return false;
    }
}
