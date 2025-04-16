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
                }else{
                    for (Player recipient : e.getRecipients()) {
                        if(recipient.hasPermission("lurienstaff.badwords")){
                            sendMessage(recipient, "&8(&cG&8) "+ PlaceholderAPI.setPlaceholders(e.getPlayer(), "%vault_prefix%")+" "+e.getPlayer().getName()+" &8» &7"+e.getMessage());
                        }
                    }
                    sendMessage(Bukkit.getConsoleSender(), "&8(&cG&8) "+ PlaceholderAPI.setPlaceholders(e.getPlayer(), "%vault_prefix%")+" "+e.getPlayer().getName()+" &8» &7"+e.getMessage());

                    e.getRecipients().removeIf(player -> player.hasPermission("lurienstaff.badwords"));

                }
            }
        }
    }
}
