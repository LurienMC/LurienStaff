package dev.lurien.staff.lurienStaff.listeners;

import dev.lurien.staff.lurienStaff.LurienStaff;
import dev.lurien.staff.lurienStaff.managers.VanishManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static dev.lurien.staff.lurienStaff.managers.VanishManager.*;

public class VanishListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if(isInVanish(p)){
            if(!p.hasPermission("lurienstaff.vanish")){
                setVanish(p, false);
            }else{
                e.setJoinMessage(null);
                setVanish(p, true);
            }
        }
        for (String inVanish : VanishManager.playersInVanish) {
            Player v = Bukkit.getPlayer(inVanish);
            if(v == null || !v.isOnline()) continue;

            if (p.hasPermission("lurienstaff.vanish")) {
                setGlowing(v, true, p);
            }else{
                p.hidePlayer(LurienStaff.getPlugin(LurienStaff.class), v);
            }
        }
    }
}
