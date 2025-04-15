package dev.lurien.staff.lurienStaff.command;

import dev.lurien.staff.lurienStaff.managers.VanishManager;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static dev.lurien.staff.lurienStaff.utils.MessagesUtils.sendMessageWithPrefix;

public class StaffListCommand implements TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if(!sender.hasPermission("lurienstaff.stafflist")){
            sendMessageWithPrefix(sender, "&c⚠ No tienes permisos.");
            return false;
        }

        sendMessageWithPrefix(sender, "&e&l! &eLista de Staffs conectados:");
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if(onlinePlayer.hasPermission("lurienstaff.staff")){
                sendMessageWithPrefix(sender, " &8 - "+ PlaceholderAPI.setPlaceholders(onlinePlayer, "%vault_prefix%")+" "+onlinePlayer.getName()+" "+(VanishManager.isInVanish(onlinePlayer) ? "(&cV) " : "")+"&7(&e"+onlinePlayer.getWorld().getName()+"&7)");
            }
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        return List.of();
    }
}
