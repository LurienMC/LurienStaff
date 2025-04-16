package dev.lurien.staff.lurienStaff.managers;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ModerationManager {

    @Getter
    private static final List<String> badWords = new ArrayList<>();
    @Getter
    private static final List<String> discriminationWords = new ArrayList<>();
    @Getter
    private static final List<String> bypassBadWords = new ArrayList<>();
    @Getter
    private static final List<String> inappropriateBehaviorWords = new ArrayList<>();

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

                isBypassed = normalizedBypass.contains(normalizedBadWord) && normalizedMessage.contains(normalizedBypass);
            }

            if (!isBypassed) {
                return true;
            }
        }

        return false;
    }

    public static boolean containsDiscriminationWord(String str) {
        String normalizedMessage = changeNumbersPerChars(removeDiacriticalMarks(str.toLowerCase()));

        for (String dWord : discriminationWords) {
            String normalizedDWord = changeNumbersPerChars(removeDiacriticalMarks(dWord.toLowerCase()));

            if (normalizedMessage.contains(normalizedDWord))
                return true;
        }

        return false;
    }

    public static boolean containsInappropriateBehaviorWords(String str) {
        String normalizedMessage = changeNumbersPerChars(removeDiacriticalMarks(str.toLowerCase()));

        for (String iWord : inappropriateBehaviorWords) {
            String normalizedIWord = changeNumbersPerChars(removeDiacriticalMarks(iWord.toLowerCase()));

            if (normalizedMessage.contains(normalizedIWord))
                return true;
        }

        return false;
    }
}
