package dev.lurien.staff.lurienStaff.managers;

import dev.lurien.bot.LurienBot;
import dev.lurien.staff.lurienStaff.LurienStaff;
import net.dv8tion.jda.api.EmbedBuilder;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static dev.lurien.staff.lurienStaff.LurienStaff.sendWebhookStaffMode;
import static dev.lurien.staff.lurienStaff.utils.MessagesUtils.logAdmins;

public class StaffModeManager {

    private static final Map<Player, Instant> enablePlayers = new HashMap<>();

    public static boolean isEnabled(Player p) {
        return enablePlayers.entrySet().stream().anyMatch(player -> player.getKey().getName().equals(p.getName()));
    }

    public static void setEnable(Player p) {
        enablePlayers.put(p, Instant.now());

        logAdmins("&e&l! &fEl staff &6"+p.getName()+"&f activó el modo staff.", p);

        EmbedBuilder embed = new EmbedBuilder().setTitle(p.getName()+" activó el modo staff")
                .setColor(0xad14ff)
                .setDescription("El staff **"+p.getName()+"** acaba de activar su servicio como staff dentro del servidor el <t:"+
                        Instant.now().getEpochSecond()+":F>.\n\n__Staff de LurienMC__")
                .setThumbnail("https://visage.surgeplay.com/full/"+p.getName())
                .setFooter("Created by @octdamfar", LurienBot.getGuild().getIconUrl());

        sendWebhookStaffMode(embed);
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    public static void setDisable(Player p, boolean quit) {
        enablePlayers.remove(enablePlayers.entrySet().stream().filter(player -> player.getKey().getName().equals(p.getName())).findAny());

        if(!quit) logAdmins("&e&l! &fEl staff &6"+p.getName()+"&f desactivó el modo staff.", p);
        else logAdmins("&e&l! &fEl staff &6"+p.getName()+"&f desactivó el modo staff saliendo del servidor.", p);

        Instant now = Instant.now();
        Instant joined = enablePlayers.get(p);

        Duration duration = Duration.between(joined, now);
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;

        EmbedBuilder embed = new EmbedBuilder().setTitle(p.getName()+" desactivó el modo staff")
                .setColor(0x7200b0)
                .setDescription("El staff **"+p.getName()+"** acaba de desactivar su servicio como staff dentro del servidor el <t:"+ Instant.now().getEpochSecond()+":F>.\n\n" +
                        "Su estancia en servicio fue de: **"+hours+"h "+minutes+"m "+seconds+"s**\n\n__Staff de LurienMC__")
                .setThumbnail("https://visage.surgeplay.com/full/"+p.getName())
                .setFooter("Created by @octdamfar", LurienBot.getGuild().getIconUrl());

        LurienStaff.getDataConfig().set(p, LurienStaff.getDataConfig().get(p)+duration.getSeconds());
        sendWebhookStaffMode(embed);
    }

    public static Map<String, String> getTopStaffsMode(int limit){
        ConfigurationSection s = LurienStaff.getDataConfig().getConfig();
        if(s.getKeys(false).isEmpty()) return new HashMap<>();

        Map<String, Long> map = new HashMap<>();

        for (String nombre : s.getKeys(false)) {
            long segundos = s.getLong(nombre);
            map.put(nombre, segundos);
        }

        return map.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(limit)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> formatTime(e.getValue()),
                        (a, b) -> a,
                        LinkedHashMap::new
                ));
    }

    private static String formatTime(Long value) {
        long horas = value / 3600;
        long minutos = (value % 3600) / 60;
        long seg = value % 60;

        return String.format("%dh %dm %ds", horas, minutos, seg);
    }
}
