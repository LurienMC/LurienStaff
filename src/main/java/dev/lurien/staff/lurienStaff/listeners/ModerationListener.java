package dev.lurien.staff.lurienStaff.listeners;

import dev.lurien.bot.LurienBot;
import dev.lurien.staff.lurienStaff.command.StaffChatCommand;
import dev.lurien.staff.lurienStaff.managers.ModerationManager;
import me.clip.placeholderapi.PlaceholderAPI;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.List;

import static dev.lurien.staff.lurienStaff.LurienStaff.sendWebhookChat;
import static dev.lurien.staff.lurienStaff.utils.MessagesUtils.sendMessage;

@SuppressWarnings("deprecation")
public class ModerationListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onChat(AsyncPlayerChatEvent e){
        if(!e.isCancelled()){
            if(ModerationManager.isSpam(e.getMessage())){
                if(StaffChatCommand.staffChatEnable.contains(e.getPlayer().getName())){
                    for (Player recipient : e.getRecipients()) {
                        if(recipient.hasPermission("lurienstaff.spam")){
                            sendMessage(recipient, "#37D7F6&lS#36CAF7&lt#35BDF8&la#34B0F9&lf#33A4FB&lf#3297FC&lC#318AFD&lh#307DFE&la#2F70FF&lt &f» &8(&cSPAM&8) "+ PlaceholderAPI.setPlaceholders(e.getPlayer(), "%vault_prefix%")+" "+e.getPlayer().getName()+" &8» &7"+e.getMessage());
                        }
                    }
                    sendMessage(Bukkit.getConsoleSender(), "#37D7F6&lS#36CAF7&lt#35BDF8&la#34B0F9&lf#33A4FB&lf#3297FC&lC#318AFD&lh#307DFE&la#2F70FF&lt &f» &8(&cSPAM&8) "+ PlaceholderAPI.setPlaceholders(e.getPlayer(), "%vault_prefix%")+" "+e.getPlayer().getName()+" &8» &7"+e.getMessage());

                    e.getRecipients().removeIf(player -> player.hasPermission("lurienstaff.spam"));
                    sendEmbedSpam(e.getPlayer(), e.getMessage(), "Staff");
                }else{
                    for (Player recipient : e.getRecipients()) {
                        if(recipient.hasPermission("lurienstaff.spam")){
                            sendMessage(recipient, "&8(&cSPAM&8) "+ PlaceholderAPI.setPlaceholders(e.getPlayer(), "%vault_prefix%")+" "+e.getPlayer().getName()+" &8» &7"+e.getMessage());
                        }
                    }
                    sendMessage(Bukkit.getConsoleSender(), "&8(&cSPAM&8) "+ PlaceholderAPI.setPlaceholders(e.getPlayer(), "%vault_prefix%")+" "+e.getPlayer().getName()+" &8» &7"+e.getMessage());

                    e.getRecipients().removeIf(player -> player.hasPermission("lurienstaff.spam"));
                    sendEmbedSpam(e.getPlayer(), e.getMessage(), "Global");
                }
            }
            else if(ModerationManager.isFlooding(e.getMessage())){
                if(StaffChatCommand.staffChatEnable.contains(e.getPlayer().getName())){
                    for (Player recipient : e.getRecipients()) {
                        if(recipient.hasPermission("lurienstaff.flood")){
                            sendMessage(recipient, "#37D7F6&lS#36CAF7&lt#35BDF8&la#34B0F9&lf#33A4FB&lf#3297FC&lC#318AFD&lh#307DFE&la#2F70FF&lt &f» &8(&cFLOOD&8) "+ PlaceholderAPI.setPlaceholders(e.getPlayer(), "%vault_prefix%")+" "+e.getPlayer().getName()+" &8» &7"+e.getMessage());
                        }
                    }
                    sendMessage(Bukkit.getConsoleSender(), "#37D7F6&lS#36CAF7&lt#35BDF8&la#34B0F9&lf#33A4FB&lf#3297FC&lC#318AFD&lh#307DFE&la#2F70FF&lt &f» &8(&cFLOOD&8) "+ PlaceholderAPI.setPlaceholders(e.getPlayer(), "%vault_prefix%")+" "+e.getPlayer().getName()+" &8» &7"+e.getMessage());

                    e.getRecipients().removeIf(player -> player.hasPermission("lurienstaff.flood"));
                    sendEmbedFlood(e.getPlayer(), e.getMessage(), "Staff");
                }else{
                    for (Player recipient : e.getRecipients()) {
                        if(recipient.hasPermission("lurienstaff.flood")){
                            sendMessage(recipient, "&8(&cFLOOD&8) "+ PlaceholderAPI.setPlaceholders(e.getPlayer(), "%vault_prefix%")+" "+e.getPlayer().getName()+" &8» &7"+e.getMessage());
                        }
                    }
                    sendMessage(Bukkit.getConsoleSender(), "&8(&cFLOOD&8) "+ PlaceholderAPI.setPlaceholders(e.getPlayer(), "%vault_prefix%")+" "+e.getPlayer().getName()+" &8» &7"+e.getMessage());

                    e.getRecipients().removeIf(player -> player.hasPermission("lurienstaff.flood"));
                    sendEmbedFlood(e.getPlayer(), e.getMessage(), "Global");
                }
            }
            else if(ModerationManager.containsBadWord(e.getMessage())){
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
                            sendMessage(recipient, "#37D7F6&lS#36CAF7&lt#35BDF8&la#34B0F9&lf#33A4FB&lf#3297FC&lC#318AFD&lh#307DFE&la#2F70FF&lt &f» &8(&cC&8) "+ PlaceholderAPI.setPlaceholders(e.getPlayer(), "%vault_prefix%")+" "+e.getPlayer().getName()+" &8» &7"+e.getMessage());
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

    private void sendEmbed(Player player, String message, String chat, String type, int color) {
        EmbedBuilder embed = new EmbedBuilder().setTitle("Posible "+type+" encontrado")
                .setColor(color)
                .setThumbnail("https://visage.surgeplay.com/full/"+player.getName())
                .setFooter("Created by @octdamfar", LurienBot.getGuild().getIconUrl());

        for (MessageEmbed.Field field : getFields(player, message, chat)) {
            embed.addField(field);
        }

        sendWebhookChat(embed);
    }

    private void sendEmbedSpam(Player player, String message, String chat) {
        EmbedBuilder embed = new EmbedBuilder().setTitle("Spam encontrado")
                .setColor(0xD3FF6C)
                .setThumbnail("https://visage.surgeplay.com/full/"+player.getName())
                .setFooter("Created by @octdamfar", LurienBot.getGuild().getIconUrl());

        for (MessageEmbed.Field field : getFields(player, message, chat)) {
            embed.addField(field);
        }

        sendWebhookChat(embed);
    }

    private void sendEmbedFlood(Player player, String message, String chat) {
        EmbedBuilder embed = new EmbedBuilder().setTitle("Flood encontrado")
                .setColor(0xFF1414)
                .setThumbnail("https://visage.surgeplay.com/full/"+player.getName())
                .setFooter("Created by @octdamfar", LurienBot.getGuild().getIconUrl());

        for (MessageEmbed.Field field : getFields(player, message, chat)) {
            embed.addField(field);
        }

        sendWebhookChat(embed);
    }

    private static List<MessageEmbed.Field> getFields(Player player, String message, String chat) {
        List<MessageEmbed.Field> fields = new ArrayList<>();

        MessageEmbed.Field field1 = new MessageEmbed.Field("Jugador", player.getName(), true);
        MessageEmbed.Field field2 = new MessageEmbed.Field("Chat", chat, true);
        MessageEmbed.Field field3 = new MessageEmbed.Field("Mensaje", message, false);
        fields.add(field1);
        fields.add(field2);
        fields.add(field3);
        return fields;
    }
}
