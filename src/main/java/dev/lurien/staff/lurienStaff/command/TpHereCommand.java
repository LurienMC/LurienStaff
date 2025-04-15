package dev.lurien.staff.lurienStaff.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static dev.lurien.staff.lurienStaff.utils.MessagesUtils.filterSuggestions;
import static dev.lurien.staff.lurienStaff.utils.MessagesUtils.sendMessageWithPrefix;

public class TpHereCommand implements TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if (sender instanceof Player p) {
            if (!sender.hasPermission("lurienstaff.tphere")) {
                sendMessageWithPrefix(sender, "&c⚠ No tienes permisos.");
                return false;
            }


            if (args.length == 0) {
                sendMessageWithPrefix(sender, "&6&l! &6Usa: /s <jugador>");
                return false;
            }

            Player t = Bukkit.getPlayerExact(args[0]);

            if (t == null || !t.isOnline()) {
                sendMessageWithPrefix(sender, "&c⚠ Ese jugador no esta conectado.");
                return false;
            }

            t.teleport(p);
            sendMessageWithPrefix(sender, "&a&l✔ &aTeletransportaste a &f"+t.getName()+" &ahacia tu posición.");
            return true;
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if(sender.hasPermission("lurienstaff.tphere") && args.length == 1){
            return filterSuggestions(Bukkit.getOnlinePlayers().stream().filter(player ->  player != sender).map(Player::getName).toList(), args[0]);
        }
        return List.of();
    }
}
