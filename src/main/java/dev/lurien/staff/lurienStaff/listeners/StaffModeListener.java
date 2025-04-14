package dev.lurien.staff.lurienStaff.listeners;

import dev.lurien.staff.lurienStaff.managers.StaffModeManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class StaffModeListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        if(e.getPlayer().hasPermission("lurienstaff.staffmode") && StaffModeManager.isEnabled(e.getPlayer())){
            StaffModeManager.setDisable(e.getPlayer(), true);
        }
    }

}
