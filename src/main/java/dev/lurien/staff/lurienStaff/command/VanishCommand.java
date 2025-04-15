package dev.lurien.staff.lurienStaff.command;

import dev.lurien.staff.lurienStaff.managers.VanishManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static dev.lurien.staff.lurienStaff.utils.MessagesUtils.sendMessageWithPrefix;

public class VanishCommand implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if(!sender.hasPermission("lurienstaff.vanish")) {
            sendMessageWithPrefix(sender, "&c⚠ No tienes permisos.");
            return false;
        }
        if(args.length > 0){
            if(sender.hasPermission("lurienstaff.headstaff")) {
                Player p = Bukkit.getPlayer(args[0]);
                if(p == null || !p.isOnline()){
                    sendMessageWithPrefix(sender, "&c⚠ Ese jugador no esta conectado.");
                    return false;
                }

                if(!p.hasPermission("luriensstaff.vanish")){
                    sendMessageWithPrefix(sender, "&c⚠ El jugador "+p.getName()+" no tiene permisos.");
                    return false;
                }
                if (!VanishManager.isInVanish(p)) {
                    VanishManager.setVanish(p, true);
                    sendMessageWithPrefix(p, "&a&l✔ &aAhora estás en vanish.");
                }else{
                    VanishManager.setVanish(p, false);
                    sendMessageWithPrefix(p, "&a&l✖ &aYa no estás en vanish.");
                }
                return true;
            }
        }
        else if(sender instanceof Player p){
            if (!VanishManager.isInVanish(p)) {
                VanishManager.setVanish(p, true);
                sendMessageWithPrefix(sender, "&a&l✔ &aAhora estás en vanish.");
            }else{
                VanishManager.setVanish(p, false);
                sendMessageWithPrefix(sender, "&a&l✖ &aYa no estás en vanish.");
            }
            return true;
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if(args.length == 1){
            if(sender.hasPermission("lurienstaff.headstaff"))
                return Bukkit.getOnlinePlayers().stream().filter(player -> player.hasPermission("lurienstaff.vanish")).map(Player::getName).toList();

        }
        return List.of();
    }
}
