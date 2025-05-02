package dev.lurien.staff.lurienStaff.listeners;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static dev.lurien.staff.lurienStaff.command.StaffChatCommand.staffChatEnable;
import static dev.lurien.staff.lurienStaff.utils.MessagesUtils.colorize;

@SuppressWarnings({"deprecation", "unchecked"})
public class StaffChatListener implements Listener {

    public static String webhook;

    @EventHandler(priority = EventPriority.NORMAL)
    public void onChat(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();

        if(staffChatEnable.contains(p.getName())){
            if(!p.hasPermission("lurienstaff.staffchat")) {
                staffChatEnable.remove(p.getName());
                return;
            }

            try {

                JSONObject json = new JSONObject();
                json.put("username", p.getName());
                json.put("avatar_url", "https://minotar.net/avatar/"+p.getName()+"/100");
                json.put("content", e.getMessage());

                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(webhook))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                        .build();

                client.send(request, HttpResponse.BodyHandlers.ofString());

            } catch (IOException | InterruptedException ex) {
                throw new RuntimeException(ex);
            }

            e.getRecipients().removeIf(player -> !player.hasPermission("lurienstaff.staffchat"));
            e.setFormat(colorize("#37D7F6&lS#36CAF7&lt#35BDF8&la#34B0F9&lf#33A4FB&lf#3297FC&lC#318AFD&lh#307DFE&la#2F70FF&lt &f» "+ PlaceholderAPI.setPlaceholders(p, "%vault_prefix%")+" "+p.getName()+" &8» &7")+e.getMessage());
        }
    }
}
