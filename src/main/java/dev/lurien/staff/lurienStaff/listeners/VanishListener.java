package dev.lurien.staff.lurienStaff.listeners;

import dev.lurien.staff.lurienStaff.LurienStaff;
import dev.lurien.staff.lurienStaff.managers.VanishManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
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
                //noinspection deprecation
                e.setJoinMessage(null);
                setVanish(p, true);
            }
        }
        for (String inVanish : VanishManager.playersInVanish) {
            Player v = Bukkit.getPlayer(inVanish);
            if(v == null || !v.isOnline()) continue;

            if (!p.hasPermission("lurienstaff.vanish")) {
                p.hidePlayer(LurienStaff.getPlugin(LurienStaff.class), v);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDamageByEntity(EntityDamageByEntityEvent e){
        if(e.getDamager() instanceof Player p && isInVanish(p))
            e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBreakBlock(BlockBreakEvent e){
        if(isInVanish(e.getPlayer()))
            e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlaceBlock(BlockPlaceEvent e){
        if(isInVanish(e.getPlayer()))
            e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPickup(EntityPickupItemEvent e){
        if(e.getEntity() instanceof Player p && isInVanish(p)) e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDrop(PlayerDropItemEvent e){
        if(isInVanish(e.getPlayer())) e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPickup(PlayerInteractEntityEvent e){
        if(isInVanish(e.getPlayer())) e.setCancelled(true);
    }
}
