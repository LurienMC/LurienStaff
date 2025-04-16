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

    private static final int MAX_CHAR_REPEAT = 6;
    private static final int MAX_WORD_REPEAT = 4;
    private static final int MIN_PATTERN_LENGTH = 2;
    private static final int MAX_PATTERN_REPEAT = 3;

    public static boolean isFlooding(String message) {
        return hasCharFlood(message) || hasWordFlood(message) || hasPatternFlood(message);
    }

    private static boolean hasCharFlood(String message) {
        int count = 1;
        for (int i = 1; i < message.length(); i++) {
            if (message.charAt(i) == message.charAt(i - 1)) {
                count++;
                if (count >= MAX_CHAR_REPEAT) {
                    return true;
                }
            } else {
                count = 1;
            }
        }
        return false;
    }

    private static boolean hasWordFlood(String message) {
        String[] words = message.toLowerCase().split("\\s+");
        int repeatCount = 1;
        for (int i = 1; i < words.length; i++) {
            if (words[i].equals(words[i - 1])) {
                repeatCount++;
                if (repeatCount >= MAX_WORD_REPEAT) {
                    return true;
                }
            } else {
                repeatCount = 1;
            }
        }
        return false;
    }

    private static boolean hasPatternFlood(String message) {
        message = message.toLowerCase().replaceAll("\\s+", "");
        for (int len = MIN_PATTERN_LENGTH; len <= message.length() / 2; len++) {
            String pattern = message.substring(0, len);
            int repetitions = 0;
            int i = 0;
            while (i + len <= message.length() && message.substring(i, i + len).equals(pattern)) {
                repetitions++;
                i += len;
                if (repetitions >= MAX_PATTERN_REPEAT) {
                    return true;
                }
            }
        }
        return false;
    }

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
