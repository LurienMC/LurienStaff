package dev.lurien.staff.lurienStaff.managers;

import org.bukkit.entity.Player;
import org.json.simple.JSONObject;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static dev.lurien.staff.lurienStaff.LurienStaff.sendWebhook;
import static dev.lurien.staff.lurienStaff.utils.MessagesUtils.logAdmins;

public class StaffModeManager {

    private static final Map<Player, Instant> enablePlayers = new HashMap<>();

    public static boolean isEnabled(Player p) {
        return enablePlayers.entrySet().stream().anyMatch(player -> player.getKey().getName().equals(p.getName()));
    }

    @SuppressWarnings("unchecked")
    public static void setEnable(Player p) {
        enablePlayers.put(p, Instant.now());

        logAdmins("&e&l! &fEl staff &6"+p.getName()+"&f activó el modo staff.", p);

        JSONObject embed = new JSONObject();
        embed.put("title", p.getName()+" activó el modo staff");
        embed.put("color", 0xad14ff);
        embed.put("description", "El staff **"+p.getName()+"** acaba de activar su servicio como staff dentro del servidor el <t:"+ Instant.now().getEpochSecond()+":F>.\n\n__Staff de LurienMC__");

        JSONObject thumbnail = new JSONObject();
        thumbnail.put("url", "https://visage.surgeplay.com/full/"+p.getName());
        embed.put("thumbnail", thumbnail);

        JSONObject footer = new JSONObject();
        footer.put("text", "Created by @octdamfar");
        footer.put("icon_url", "https://media.discordapp.net/attachments/1320172178469027932/1361184687497805945/logo.png?ex=67fdd587&is=67fc8407&hm=e58cc6f503625da4ac30cca9e2316ba35b0626e7d236b178b2ae652d216da93a&=&format=webp&quality=lossless&width=350&height=350");
        embed.put("footer", footer);

        sendWebhook(embed);
    }

    @SuppressWarnings({"SuspiciousMethodCalls", "unchecked"})
    public static void setDisable(Player p) {
        enablePlayers.remove(enablePlayers.entrySet().stream().filter(player -> player.getKey().getName().equals(p.getName())).findAny());

        logAdmins("&e&l! &fEl staff &6"+p.getName()+"&f desactivó el modo staff.", p);

        Instant now = Instant.now();
        Instant joined = enablePlayers.get(p);

        Duration duration = Duration.between(joined, now);
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;

        JSONObject embed = new JSONObject();
        embed.put("title", p.getName()+" desactivó el modo staff");
        embed.put("color", 0x7200b0);
        embed.put("description", "El staff **"+p.getName()+"** acaba de desactivar su servicio como staff dentro del servidor el <t:"+ Instant.now().getEpochSecond()+":F>.\n\n" +
                "Su estancia en servicio fue de: **"+hours+"h "+minutes+"m "+seconds+"s**\n\n__Staff de LurienMC__");

        JSONObject thumbnail = new JSONObject();
        thumbnail.put("url", "https://visage.surgeplay.com/full/"+p.getName());
        embed.put("thumbnail", thumbnail);

        JSONObject footer = new JSONObject();
        footer.put("text", "Created by @octdamfar");
        footer.put("icon_url", "https://media.discordapp.net/attachments/1320172178469027932/1361184687497805945/logo.png?ex=67fdd587&is=67fc8407&hm=e58cc6f503625da4ac30cca9e2316ba35b0626e7d236b178b2ae652d216da93a&=&format=webp&quality=lossless&width=350&height=350");
        embed.put("footer", footer);

        sendWebhook(embed);
    }
}
