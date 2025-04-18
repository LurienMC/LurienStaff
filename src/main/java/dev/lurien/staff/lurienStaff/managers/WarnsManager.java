package dev.lurien.staff.lurienStaff.managers;

import dev.lurien.staff.lurienStaff.LurienStaff;
import dev.lurien.staff.lurienStaff.model.Warn;
import dev.lurien.staff.lurienStaff.model.WarnReason;
import net.dv8tion.jda.api.EmbedBuilder;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static dev.lurien.staff.lurienStaff.utils.MessagesUtils.broadcast;

public class WarnsManager {
    public static final List<Warn> warns = new ArrayList<>();

    public static void load(){
        ConfigurationSection section = LurienStaff.getDataConfig().getWarnsSection();
        for (String key : section.getKeys(false)) {
            OfflinePlayer op = Bukkit.getOfflinePlayer(key);
            ConfigurationSection s = section.getConfigurationSection(key);
            assert s != null;
            for (String sKey : s.getKeys(false)) {
                String reason = s.getString(sKey+".razón", "Ninguna"), staff = s.getString(sKey+".staff", "Luriencito");
                warns.add(new Warn(op, reason, WarnReason.getWarnByName(reason), staff));
            }
        }
    }

    public static void save(String player){
        if(hasWarns(player)){
            ConfigurationSection section = LurienStaff.getDataConfig().getWarnsSection();
            ConfigurationSection s = section.createSection(player);
            int i = 0;
            for (Warn warn : getPlayerWarns(player)) {
                s.set(i+".razón", warn.getReason() == null ? "Ninguna" : warn.getReason());
                s.set(i+".staff", warn.getStaff() == null ? "Luriencito" : warn.getStaff());
                i++;
            }
            LurienStaff.getDataConfig().save();
        }
    }

    public static void saveAll(){
        for (String player : getAllPlayerWarns()) {
            save(player);
        }
    }

    private static List<String> getAllPlayerWarns() {
        Set<String> names = new HashSet<>();

        for (Warn warn : warns) {
            if(warn.getPlayer().getName() == null) continue;
            names.add(warn.getPlayer().getName());
        }
        return new ArrayList<>(names);
    }

    private static List<Warn> getPlayerWarns(String player) {
        return warns.stream().filter(warn -> warn.getPlayer().getName() != null && warn.getPlayer().getName().equals(player)).toList();
    }

    private static boolean hasWarns(String player) {
        return warns.stream().anyMatch(warn -> warn.getPlayer().getName() != null && warn.getPlayer().getName().equals(player));
    }

    public static void warn(OfflinePlayer player, @NotNull CommandSender staff, String reason) {
        Warn warn = new Warn(player, reason, WarnReason.getWarnByName(reason), (staff instanceof Player ? staff.getName() : "Luriencito"));

        broadcast(
                "#f57def&l================[#f5000c&lX#f57def]================",
                " ",
                "                    #f51d7b&lADVERTENCIA",
                " ",
                "              #b000e6&lUsuario: &f"+player.getName(),
                "              #b000e6&lStaff: &f"+(staff instanceof Player ? staff.getName() : "Luriencito"),
                "              #b000e6&lRazón: &f"+reason+" ("+(getWarns(player, reason)+1)+(WarnReason.getWarnByName(reason) == null ? ")" : "/"+Objects.requireNonNull(WarnReason.getWarnByName(reason)).maxWarnings+")"),
                " ",
                "#f57def&l==================================");

        warns.add(warn);
        save(player.getName());

        EmbedBuilder eb = new EmbedBuilder()
                .setColor(0xe67935)
                .setFooter("por "+(staff instanceof Player ? staff.getName() : "Luriencito"))
                .setDescription("***"+player.getName()+" fue advertido.*** | "+reason+" ("+getWarns(player, reason)+(WarnReason.getWarnByName(reason) == null ? ")" : "/"+Objects.requireNonNull(WarnReason.getWarnByName(reason)).maxWarnings+")"));

        LurienStaff.getModerationLogsChannel().sendMessageEmbeds(eb.build()).queue();
    }

    private static int getWarns(OfflinePlayer player, String reason) {
        int i = 0;
        for (Warn warn : getPlayerWarns(player.getName())) {
            if(warn.getReason().equalsIgnoreCase(reason) || (warn.getWarnReason() != null && WarnReason.getWarnByName(reason) != null && warn.getWarnReason().equals(WarnReason.getWarnByName(reason)))){
                i++;
            }
        }
        return i;
    }
}
