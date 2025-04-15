package dev.lurien.staff.lurienStaff.listeners;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import static dev.lurien.staff.lurienStaff.command.StaffChatCommand.staffChatEnable;

@SuppressWarnings("deprecation")
public class StaffChatListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();

        if(staffChatEnable.contains(p.getName())){
            if(!p.hasPermission("lurienstaff.staffchat")) {
                staffChatEnable.remove(p.getName());
                return;
            }

            e.getRecipients().removeIf(player -> !player.hasPermission("lurienstaff.staffchat"));
            e.setFormat("#37D7F6&lS#36CAF7&lt#35BDF8&la#34B0F9&lf#33A4FB&lf#3297FC&lC#318AFD&lh#307DFE&la#2F70FF&lt &f» "+ PlaceholderAPI.setPlaceholders(p, "%vault_prefix%")+" "+p.getName()+" &8» &7"+e.getMessage());
        }
    }
}
