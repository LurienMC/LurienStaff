package dev.lurien.staff.lurienStaff.model;

import lombok.Getter;

@Getter
public enum WarnReason {
    BAD_BEHAVIOR("Mal Comportamiento", 2, new Punishment(PunishmentType.MUTE, 60 * 20)),
    TOXICITY("Toxicidad", 3, new Punishment(PunishmentType.MUTE, 60 * 30)),
    SPAM("Spam", 2, new Punishment(PunishmentType.MUTE, 60 * 30)),
    FLOOD("Flood", 3, new Punishment(PunishmentType.MUTE, 60 * 45));

    public final String capitalizeName;
    public final int maxWarnings;
    public final Punishment punishment;

    WarnReason(String capitalizeName, int maxWarnings, Punishment punishment) {
        this.capitalizeName = capitalizeName;
        this.maxWarnings = maxWarnings;
        this.punishment = punishment;
    }

    public static boolean isReason(String r){
        for (WarnReason value : values()) {
            if(value.capitalizeName.equalsIgnoreCase(r)) return true;
        }
        return false;
    }

    public static WarnReason getWarnByName(String name){
        for (WarnReason value : values()) {
            if(value.capitalizeName.equalsIgnoreCase(name)) return value;
        }
        return null;
    }

    public static String capitalizeEveryWord(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        String[] words = str.split("\\s+");
        StringBuilder sb = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                String capitalizedWord = word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
                sb.append(capitalizedWord).append(" ");
            }
        }

        if (!sb.isEmpty() && str.charAt(str.length() - 1) != ' ') {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
}
