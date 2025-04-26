package dev.lurien.staff.lurienStaff.managers;

import dev.lurien.staff.lurienStaff.LurienStaff;
import dev.lurien.staff.lurienStaff.model.Warn;
import dev.lurien.staff.lurienStaff.model.WarnReason;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
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
                String fechaStr = s.getString(sKey+".fecha");
                LocalDateTime fecha = fechaStr != null ? LocalDateTime.parse(fechaStr) : LocalDateTime.now(); // fallback por compatibilidad
                warns.add(new Warn(op, UUID.fromString(Objects.requireNonNull(s.getString(sKey + ".id"))), reason, WarnReason.getWarnByName(reason), fecha, staff));
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
                s.set(i+".id", warn.getId().toString());
                s.set(i+".fecha", warn.getDate().toString());
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

    public static List<Warn> getPlayerWarns(String player) {
        return warns.stream().filter(warn -> warn.getPlayer().getName() != null && warn.getPlayer().getName().equals(player)).toList();
    }

    public static boolean hasWarns(String player) {
        return warns.stream().anyMatch(warn -> warn.getPlayer().getName() != null && warn.getPlayer().getName().equals(player));
    }

    public static void warn(OfflinePlayer player, @NotNull CommandSender staff, String reason) {
        reason = WarnReason.capitalizeEveryWord(reason);
        Warn warn = new Warn(player, UUID.randomUUID(), reason, WarnReason.getWarnByName(reason), LocalDateTime.now(), (staff instanceof Player ? staff.getName() : "Luriencito"));

        broadcast(
                "<center>#f57def=====================[#f5000c&lX#f57def]=====================",
                " ",
                "<center>#f51d7b&lADVERTENCIA",
                " ",
                "                   #b000e6&lUsuario: &f" + player.getName(),
                "                   #b000e6&lStaff: &f" + (staff instanceof Player ? staff.getName() : "Luriencito"),
                "                   #b000e6&lRazón: &f" + reason + " (" + (getWarns(player, reason) + 1) + (WarnReason.getWarnByName(reason) == null ? ")" : "/" + Objects.requireNonNull(WarnReason.getWarnByName(reason)).maxWarnings + ")"),
                " ",
                "<center>#f57def&l======================================================");

        warns.add(warn);
        save(player.getName());

        EmbedBuilder eb = new EmbedBuilder()
                .setColor(0xe67935)
                .setDescription("***" + player.getName() + " fue advertido.*** | " + reason + " (" + getWarns(player, reason) + (WarnReason.getWarnByName(reason) == null ? ")" : "/" + Objects.requireNonNull(WarnReason.getWarnByName(reason)).maxWarnings + ")"));


        EmbedBuilder eb2 = new EmbedBuilder()
                .setColor(0xff0000)
                .setTitle("Un jugador ha sido advertido")
                .addField("Jugador", Objects.requireNonNull(player.getName()), true)
                .addField("Staff", (staff instanceof Player ? staff.getName() : "Consola (interpretada como Luriencito IG)"), false)
                .addField("Razón", reason, true)
                .addField("Advertencias", getWarns(player, reason)+(WarnReason.getWarnByName(reason) == null ? ")" : "/"+Objects.requireNonNull(WarnReason.getWarnByName(reason)).maxWarnings), true)
                .setFooter("Created by @octdamfar")
                .setThumbnail("https://visage.surgeplay.com/full/"+player.getName());

        LurienStaff.getModerationLogsChannel().sendMessageEmbeds(eb.build()).queue();
        LurienStaff.getActivityChannel().sendMessageEmbeds(eb2.build())
                .setActionRow(Button.danger("dw;"+warn.getId(), "Quitar advertencia"),
                        Button.primary("crw;"+warn.getId(), "Cambiar Razón")).queue();
    }

    public static void changeReason(Warn warn, Member member, String newReason) {
        newReason = WarnReason.capitalizeEveryWord(newReason);

        broadcast(
                "<center>#f57def=====================[#f5000c&lX#f57def]=====================",
                " ",
                "<center>#f51d7b&lCAMBIO DE RAZÓN #f51d7b(Advertencia)",
                " ",
                "                   #b000e6&lUsuario: &f" + warn.getPlayer().getName(),
                "                   #b000e6&lAdvertido por: &f" + warn.getStaff(),
                "                   #b000e6&lRazón: &f&m" + warn.getReason() + " (" + (getWarns(warn.getPlayer(), warn.getReason())) + (WarnReason.getWarnByName(warn.getReason()) == null ? ")" : "/" + Objects.requireNonNull(WarnReason.getWarnByName(warn.getReason())).maxWarnings + ")&f -> "+ newReason + " (" + ((getWarns(warn.getPlayer(), newReason)) + 1)) + (WarnReason.getWarnByName(newReason) == null ? ")" : "/" + Objects.requireNonNull(WarnReason.getWarnByName(newReason)).maxWarnings + ")"),
                "                   #b000e6&lCambiado por: &f" + member.getEffectiveName() + " (desde Discord)",
                " ",
                "<center>#f57def&l======================================================");

        EmbedBuilder eb = new EmbedBuilder()
                .setColor(0xe67935)
                .setDescription("***Una advertencia de " + warn.getPlayer().getName() + " fue cambiada de razón.*** | " + warn.getReason()+ " -> "+newReason);

        EmbedBuilder eb2 = new EmbedBuilder()
                .setColor(0xff0000)
                .setTitle("Razón cambiada en una advertencia")
                .addField("Jugador", Objects.requireNonNull(warn.getPlayer().getName()), true)
                .addField("Quién advirtió", !warn.getStaff().equals("Luriencito") ? warn.getStaff() : "Consola (interpretada como Luriencito IG)", false)
                .addField("Quién cambió", member.getAsMention(), false)
                .addField("Razón", warn.getReason()+" -> "+newReason, true)
                .setFooter("Created by @octdamfar")
                .setThumbnail("https://visage.surgeplay.com/full/"+warn.getPlayer().getName());

        warn.setReason(newReason);
        warn.setWarnReason(WarnReason.getWarnByName(newReason));
        save(warn.getPlayer().getName());

        LurienStaff.getModerationLogsChannel().sendMessageEmbeds(eb.build()).queue();
        LurienStaff.getActivityChannel().sendMessageEmbeds(eb2.build())
                .setActionRow(Button.danger("dw;"+warn.getId(), "Quitar advertencia"),
                        Button.primary("crw;"+warn.getId(), "Cambiar Razón")).queue();
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

    public static Warn getWarnById(UUID id) {
        return warns.stream().filter(warn -> warn.getId().equals(id)).findAny().orElse(null);
    }

    public static void removeWarn(Warn warn, Member member) {
        EmbedBuilder eb = new EmbedBuilder()
                .setColor(0xe67935)
                .setDescription("***Una advertencia de " + warn.getPlayer().getName() + " por " + warn.getReason() + " fue removida.***");

        LurienStaff.getModerationLogsChannel().sendMessageEmbeds(eb.build()).queue();

        broadcast(
                "<center>#f57def=====================[#f5000c&lX#f57def]=====================",
                " ",
                "<center>#f51d7b&lADVERTENCIA REMOVIDA",
                " ",
                "                   #b000e6&lUsuario: &f" + warn.getPlayer().getName(),
                "                   #b000e6&lRazón: &f" + warn.getReason() + " (" + (getWarns(warn.getPlayer(), warn.getReason()) + 1) + (WarnReason.getWarnByName(warn.getReason()) == null ? ")" : "/" + Objects.requireNonNull(WarnReason.getWarnByName(warn.getReason())).maxWarnings + ")"),
                "                   #b000e6&lAdvertido por: &f" + warn.getStaff(),
                "                   #b000e6&lRemovido por: &f" + member.getEffectiveName()+" (desde Discord)",
                " ",
                "<center>#f57def&l======================================================");
        warns.remove(warn);
        save(warn.getPlayer().getName());

        EmbedBuilder eb2 = new EmbedBuilder()
                .setColor(0xff0000)
                .setTitle("Una advertencia fue removida")
                .addField("Reportado", Objects.requireNonNull(warn.getPlayer().getName()), true)
                .addField("Quien advirtió", !warn.getStaff().equals("Luriencito") ? warn.getStaff() : "Consola (interpretada como Luriencito IG)", false)
                .addField("Quien removió", member.getAsMention(), false)
                .addField("Razón", warn.getReason(), true)
                .setFooter("Created by @octdamfar")
                .setThumbnail("https://visage.surgeplay.com/full/"+warn.getPlayer().getName());
        LurienStaff.sendWebhookActivity(eb2);
    }
}
