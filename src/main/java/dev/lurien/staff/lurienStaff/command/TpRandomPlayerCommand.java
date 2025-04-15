package dev.lurien.staff.lurienStaff.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

import static dev.lurien.staff.lurienStaff.utils.MessagesUtils.sendMessageWithPrefix;

public class TpRandomPlayerCommand implements TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if(sender instanceof Player p) {
            if (!p.hasPermission("lurienstaff.tprandomplayer")) {
                sendMessageWithPrefix(sender, "&c⚠ No tienes permisos.");
                return false;
            }

            if(Bukkit.getOnlinePlayers().size() < 2){
                sendMessageWithPrefix(sender, "&c⚠ No hay a quien teletransportarse.");
                return false;
            }

            List<? extends Player> pl = Bukkit.getOnlinePlayers().stream().filter(player -> player != p).toList();
            Player rp = pl.get(new Random().nextInt(pl.size()));

            p.teleport(rp);
            sendMessageWithPrefix(sender, "&a&l✔ &aTeletransportandote hacia &f"+rp.getName()+"&a.");
            return true;
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        return List.of();
    }
}
