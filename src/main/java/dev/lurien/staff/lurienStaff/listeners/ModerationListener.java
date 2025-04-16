package dev.lurien.staff.lurienStaff.listeners;

import dev.lurien.staff.lurienStaff.command.StaffChatCommand;
import dev.lurien.staff.lurienStaff.managers.ModerationManager;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.simple.JSONObject;

import java.time.Instant;

import static dev.lurien.staff.lurienStaff.LurienStaff.sendWebhookChat;
import static dev.lurien.staff.lurienStaff.utils.MessagesUtils.sendMessage;

@SuppressWarnings("deprecation")
public class ModerationListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onChat(AsyncPlayerChatEvent e){
        if(!e.isCancelled()){
            if(ModerationManager.containsBadWord(e.getMessage())){
                if(StaffChatCommand.staffChatEnable.contains(e.getPlayer().getName())){
                    for (Player recipient : e.getRecipients()) {
                        if(recipient.hasPermission("lurienstaff.badwords")){
                            sendMessage(recipient, "#37D7F6&lS#36CAF7&lt#35BDF8&la#34B0F9&lf#33A4FB&lf#3297FC&lC#318AFD&lh#307DFE&la#2F70FF&lt &f» &8(&cG&8) "+ PlaceholderAPI.setPlaceholders(e.getPlayer(), "%vault_prefix%")+" "+e.getPlayer().getName()+" &8» &7"+e.getMessage());
                        }
                    }
                    sendMessage(Bukkit.getConsoleSender(), "#37D7F6&lS#36CAF7&lt#35BDF8&la#34B0F9&lf#33A4FB&lf#3297FC&lC#318AFD&lh#307DFE&la#2F70FF&lt &f» &8(&cG&8) "+ PlaceholderAPI.setPlaceholders(e.getPlayer(), "%vault_prefix%")+" "+e.getPlayer().getName()+" &8» &7"+e.getMessage());

                    e.getRecipients().removeIf(player -> player.hasPermission("lurienstaff.badwords"));
                    sendEmbed(e.getPlayer(), e.getMessage(), "Staff", "GROSERÍA", 0x37F682);
                }else{
                    for (Player recipient : e.getRecipients()) {
                        if(recipient.hasPermission("lurienstaff.badwords")){
                            sendMessage(recipient, "&8(&cG&8) "+ PlaceholderAPI.setPlaceholders(e.getPlayer(), "%vault_prefix%")+" "+e.getPlayer().getName()+" &8» &7"+e.getMessage());
                        }
                    }
                    sendMessage(Bukkit.getConsoleSender(), "&8(&cG&8) "+ PlaceholderAPI.setPlaceholders(e.getPlayer(), "%vault_prefix%")+" "+e.getPlayer().getName()+" &8» &7"+e.getMessage());

                    e.getRecipients().removeIf(player -> player.hasPermission("lurienstaff.badwords"));
                    sendEmbed(e.getPlayer(), e.getMessage(), "Global", "GROSERÍA", 0x37F682);
                }
            }else if(ModerationManager.containsDiscriminationWord(e.getMessage())){
                if(StaffChatCommand.staffChatEnable.contains(e.getPlayer().getName())){
                    for (Player recipient : e.getRecipients()) {
                        if(recipient.hasPermission("lurienstaff.discrimination")){
                            sendMessage(recipient, "#37D7F6&lS#36CAF7&lt#35BDF8&la#34B0F9&lf#33A4FB&lf#3297FC&lC#318AFD&lh#307DFE&la#2F70FF&lt &f» &8(&cDC&8) "+ PlaceholderAPI.setPlaceholders(e.getPlayer(), "%vault_prefix%")+" "+e.getPlayer().getName()+" &8» &7"+e.getMessage());
                        }
                    }
                    sendMessage(Bukkit.getConsoleSender(), "#37D7F6&lS#36CAF7&lt#35BDF8&la#34B0F9&lf#33A4FB&lf#3297FC&lC#318AFD&lh#307DFE&la#2F70FF&lt &f» &8(&cDC&8) "+ PlaceholderAPI.setPlaceholders(e.getPlayer(), "%vault_prefix%")+" "+e.getPlayer().getName()+" &8» &7"+e.getMessage());

                    e.getRecipients().removeIf(player -> player.hasPermission("lurienstaff.discrimination"));
                    sendEmbed(e.getPlayer(), e.getMessage(), "Staff", "DISCRIMINACIÓN", 0xFFE449);
                }else{
                    for (Player recipient : e.getRecipients()) {
                        if(recipient.hasPermission("lurienstaff.discrimination")){
                            sendMessage(recipient, "&8(&cDC&8) "+ PlaceholderAPI.setPlaceholders(e.getPlayer(), "%vault_prefix%")+" "+e.getPlayer().getName()+" &8» &7"+e.getMessage());
                        }
                    }
                    sendMessage(Bukkit.getConsoleSender(), "&8(&cDC&8) "+ PlaceholderAPI.setPlaceholders(e.getPlayer(), "%vault_prefix%")+" "+e.getPlayer().getName()+" &8» &7"+e.getMessage());

                    e.getRecipients().removeIf(player -> player.hasPermission("lurienstaff.discrimination"));
                    sendEmbed(e.getPlayer(), e.getMessage(), "Global", "DISCRIMINACIÓN", 0xFFE449);
                }
            }else if(ModerationManager.containsInappropriateBehaviorWords(e.getMessage())){
                if(StaffChatCommand.staffChatEnable.contains(e.getPlayer().getName())){
                    for (Player recipient : e.getRecipients()) {
                        if(recipient.hasPermission("lurienstaff.inappropriatebehavior")){
                            sendMessage(recipient, "#37D7F6&lS#36CAF7&lt#35BDF8&la#34B0F9&lf#33A4FB&lf#3297FC&lC#318AFD&lh#307DFE&la#2F70FF&lt &f» &8(&cCC&8) "+ PlaceholderAPI.setPlaceholders(e.getPlayer(), "%vault_prefix%")+" "+e.getPlayer().getName()+" &8» &7"+e.getMessage());
                        }
                    }
                    sendMessage(Bukkit.getConsoleSender(), "#37D7F6&lS#36CAF7&lt#35BDF8&la#34B0F9&lf#33A4FB&lf#3297FC&lC#318AFD&lh#307DFE&la#2F70FF&lt &f» &8(&cC&8) "+ PlaceholderAPI.setPlaceholders(e.getPlayer(), "%vault_prefix%")+" "+e.getPlayer().getName()+" &8» &7"+e.getMessage());

                    e.getRecipients().removeIf(player -> player.hasPermission("lurienstaff.inappropriatebehavior"));
                    sendEmbed(e.getPlayer(), e.getMessage(), "Staff", "MAL COMPORTAMIENTO", 0xFF6E49);
                }else{
                    for (Player recipient : e.getRecipients()) {
                        if(recipient.hasPermission("lurienstaff.inappropriatebehavior")){
                            sendMessage(recipient, "&8(&cC&8) "+ PlaceholderAPI.setPlaceholders(e.getPlayer(), "%vault_prefix%")+" "+e.getPlayer().getName()+" &8» &7"+e.getMessage());
                        }
                    }
                    sendMessage(Bukkit.getConsoleSender(), "&8(&cC&8) "+ PlaceholderAPI.setPlaceholders(e.getPlayer(), "%vault_prefix%")+" "+e.getPlayer().getName()+" &8» &7"+e.getMessage());

                    e.getRecipients().removeIf(player -> player.hasPermission("lurienstaff.inappropriatebehavior"));
                    sendEmbed(e.getPlayer(), e.getMessage(), "Global", "MAL COMPORTAMIENTO", 0xFF6E49);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void sendEmbed(Player player, String message, String chat, String type, int color) {
        JSONObject embed = new JSONObject();
        embed.put("title", "Posible "+type+" encontrado");
        embed.put("color", color);

        JSONObject thumbnail = new JSONObject();
        thumbnail.put("url", "https://visage.surgeplay.com/full/"+player.getName());
        embed.put("thumbnail", thumbnail);

        JSONArray fields = new JSONArray();

        JSONObject field1 = new JSONObject();
        field1.put("name", "Jugador");
        field1.put("value", player.getName());
        field1.put("inline", true);

        JSONObject field2 = new JSONObject();
        field2.put("name", "Chat del mensaje");
        field2.put("value", chat);
        field2.put("inline", true);

        JSONObject field3 = new JSONObject();
        field3.put("name", "Mensaje completo");
        field3.put("value", message);
        field3.put("inline", false);

        fields.put(field1);
        fields.put(field2);
        fields.put(field3);
        embed.put("fields", fields);

        JSONObject footer = new JSONObject();
        footer.put("text", "Created by @octdamfar");
        footer.put("icon_url", "https://media.discordapp.net/attachments/1320172178469027932/1361184687497805945/logo.png?ex=67fdd587&is=67fc8407&hm=e58cc6f503625da4ac30cca9e2316ba35b0626e7d236b178b2ae652d216da93a&=&format=webp&quality=lossless&width=350&height=350");
        embed.put("footer", footer);

        sendWebhookChat(embed);
    }
}
