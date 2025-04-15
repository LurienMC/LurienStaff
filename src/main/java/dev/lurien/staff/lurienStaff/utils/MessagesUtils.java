package dev.lurien.staff.lurienStaff.utils;

import lombok.Getter;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@SuppressWarnings({"deprecation", "unused"})
public class MessagesUtils {

    private final static int CENTER_PX = 154;
    public final static String PREFIX = "#D268FF&lL#D858FF&lu#DE49FF&lr#E439FF&li#EB6BFF&le#F29DFF&ln#CF5EFF&lM#CF5EFF&lC &f»";

    public static void log(String message){
        sendMessage(Bukkit.getConsoleSender(), message);
    }

    public static void logAdmins(String s, Player... exclude){
        sendMessageWithPrefix(Bukkit.getConsoleSender(), s);
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if(onlinePlayer.hasPermission("lurienstaff.headstaff") && Arrays.stream(exclude).noneMatch(player -> player.getName().equals(onlinePlayer.getName()))){
                sendMessageWithPrefix(onlinePlayer, s);
            }
        }
    }

    public static void broadcast(String message){
        Bukkit.broadcastMessage(colorize(message.replace("%prefix%", PREFIX)));
    }

    public static void sendMessage(CommandSender sender, String s) {
        if(sender instanceof Player) PlaceholderAPI.setPlaceholders((Player) sender, s);
        if(s.startsWith("<center>")) s = getCenteredMessage(s.replace("<center>", ""));
        else if(s.startsWith("<underline:")){
            if(s.length() == 12){
                sendUnderline(sender, s.charAt(11));
            }else{
                sendUnderline(sender, s.replace("<underline:", ""));
            }
            return;
        }
        if(sender instanceof Player){
            sender.sendMessage(colorize(s.replace("%prefix%", PREFIX)));
        }else{
            sender.sendMessage(colorize(s));

        }
    }

    public static List<String> filterSuggestions(List<String> suggestions, String currentInput) {
        if (currentInput.isEmpty()) {
            return suggestions;
        }

        return suggestions.stream()
                .filter(s -> s.toLowerCase().startsWith(currentInput.toLowerCase()))
                .collect(Collectors.toList());
    }

    public static void sendMessageWithPrefix(CommandSender sender, String s){
        sendMessage(sender, PREFIX+" "+s);
    }

    public static void sendMessageWithPrefix(CommandSender sender, List<String> list){
        for (String s : list) {
            sendMessageWithPrefix(sender, s);
        }
    }

    public static void sendMessage(CommandSender sender, List<String> list) {
        for (String s : list) {
            sendMessage(sender, s);
        }
    }

    public static List<String> colorateList(List<String> lines) {
        lines.replaceAll(MessagesUtils::colorize);
        return lines;
    }

    public static void replace(List<String> list, String str1, String str2){
        list.replaceAll(s -> s.replace(str1, str2));
    }

    public static boolean contains(List<String> list, String search){
        for (String s : list) {
            if(s.toLowerCase().contains(search.toLowerCase())) return true;
        }
        return false;
    }

    public static String colorize(String message) {
        if(message == null) message = "";
        if(ServerVersion.v1_16_R1.equalsOrGraterVersion()) {
            Pattern hexPattern = Pattern.compile("#[a-fA-F0-9]{6}");
            Matcher matcher = hexPattern.matcher(message);

            while (matcher.find()) {
                String hexColor = matcher.group();
                StringBuilder minecraftColor = new StringBuilder("§x");
                for (char c : hexColor.substring(1).toCharArray()) {
                    minecraftColor.append("§").append(c);
                }
                message = message.replace(hexColor, minecraftColor.toString());
            }
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String getCenteredMessage(String message){
        message = colorize(message);

        int messagePxSize = 0;
        boolean previousCode = false;
        boolean isBold = false;

        for(char c : message.toCharArray()){
            if(c == '§'){
                previousCode = true;
            }else if(previousCode){
                previousCode = false;
                isBold = c == 'l' || c == 'L';
            }else{
                DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
                messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
                messagePxSize++;
            }
        }

        int halvedMessageSize = messagePxSize / 2;
        int toCompensate = CENTER_PX - halvedMessageSize;
        int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
        int compensated = 0;
        StringBuilder sb = new StringBuilder();
        while(compensated < toCompensate){
            sb.append(" ");
            compensated += spaceLength;
        }
        return sb +message;
    }

    public static void sendCenteredMessage(CommandSender s, String message){
        s.sendMessage(getCenteredMessage(message));
    }

    public static void sendUnderline(CommandSender sender, String hex) {
        sender.sendMessage(colorize(hex+"&m                                                                          "));
    }


    public static void sendUnderline(CommandSender sender, char color) {
        sender.sendMessage(colorize("&"+color+"&m                                                                          "));
    }

    @Getter
    private enum DefaultFontInfo {

        A('A', 5),
        a('a', 5),
        B('B', 5),
        b('b', 5),
        C('C', 5),
        c('c', 5),
        D('D', 5),
        d('d', 5),
        E('E', 5),
        e('e', 5),
        F('F', 5),
        f('f', 4),
        G('G', 5),
        g('g', 5),
        H('H', 5),
        h('h', 5),
        I('I', 3),
        i('i', 1),
        J('J', 5),
        j('j', 5),
        K('K', 5),
        k('k', 4),
        L('L', 5),
        l('l', 1),
        M('M', 5),
        m('m', 5),
        N('N', 5),
        n('n', 5),
        O('O', 5),
        o('o', 5),
        P('P', 5),
        p('p', 5),
        Q('Q', 5),
        q('q', 5),
        R('R', 5),
        r('r', 5),
        S('S', 5),
        s('s', 5),
        T('T', 5),
        t('t', 4),
        U('U', 5),
        u('u', 5),
        V('V', 5),
        v('v', 5),
        W('W', 5),
        w('w', 5),
        X('X', 5),
        x('x', 5),
        Y('Y', 5),
        y('y', 5),
        Z('Z', 5),
        z('z', 5),
        NUM_1('1', 5),
        NUM_2('2', 5),
        NUM_3('3', 5),
        NUM_4('4', 5),
        NUM_5('5', 5),
        NUM_6('6', 5),
        NUM_7('7', 5),
        NUM_8('8', 5),
        NUM_9('9', 5),
        NUM_0('0', 5),
        EXCLAMATION_POINT('!', 1),
        AT_SYMBOL('@', 6),
        NUM_SIGN('#', 5),
        DOLLAR_SIGN('$', 5),
        PERCENT('%', 5),
        UP_ARROW('^', 5),
        AMPERSAND('&', 5),
        ASTERISK('*', 5),
        LEFT_PARENTHESIS('(', 4),
        RIGHT_PERENTHESIS(')', 4),
        MINUS('-', 5),
        UNDERSCORE('_', 5),
        PLUS_SIGN('+', 5),
        EQUALS_SIGN('=', 5),
        LEFT_CURL_BRACE('{', 4),
        RIGHT_CURL_BRACE('}', 4),
        LEFT_BRACKET('[', 3),
        RIGHT_BRACKET(']', 3),
        COLON(':', 1),
        SEMI_COLON(';', 1),
        DOUBLE_QUOTE('"', 3),
        SINGLE_QUOTE('\'', 1),
        LEFT_ARROW('<', 4),
        RIGHT_ARROW('>', 4),
        QUESTION_MARK('?', 5),
        SLASH('/', 5),
        BACK_SLASH('\\', 5),
        LINE('|', 1),
        TILDE('~', 5),
        TICK('`', 2),
        PERIOD('.', 1),
        COMMA(',', 1),
        SPACE(' ', 3),
        DEFAULT('a', 4);

        private final char character;
        private final int length;

        DefaultFontInfo(char character, int length) {
            this.character = character;
            this.length = length;
        }

        public int getBoldLength() {
            if (this == DefaultFontInfo.SPACE) return this.getLength();
            return this.length + 1;
        }

        public static DefaultFontInfo getDefaultFontInfo(char c) {
            for (DefaultFontInfo dFI : DefaultFontInfo.values()) {
                if (dFI.getCharacter() == c) return dFI;
            }
            return DefaultFontInfo.DEFAULT;
        }
    }
}
