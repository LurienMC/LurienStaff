package dev.lurien.staff.lurienStaff.command;

import dev.lurien.staff.lurienStaff.managers.WarnsManager;
import dev.lurien.staff.lurienStaff.model.WarnReason;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


import java.util.Arrays;
import java.util.List;

import static dev.lurien.staff.lurienStaff.utils.MessagesUtils.filterSuggestions;
import static dev.lurien.staff.lurienStaff.utils.MessagesUtils.sendMessageWithPrefix;

public class WarnCommand implements TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if(!sender.hasPermission("lurienstaff.warn")){
            sendMessageWithPrefix(sender, "&c⚠ No tienes permisos.");
            return false;
        }

        if(args.length < 2){
            sendMessageWithPrefix(sender, "&6&l! &6Usa: /warn <jugador> <razón..>");
            return false;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if(!target.hasPlayedBefore()){
            sendMessageWithPrefix(sender, "&c⚠ El jugador "+target+" nunca ha jugado en el servidor.");
            return false;
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 1; i < args.length; i++) {
            sb.append(args[i]).append(" ");
        }

        String fs = sb.toString().trim();

        if(!WarnReason.isReason(fs) && !sender.hasPermission("lurienstaff.headstaff")){
            sendMessageWithPrefix(sender, "&c⚠ Esa no es una razón válida para una advertencia.");
            return false;
        }

        WarnsManager.warn(target, sender, fs);
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if(sender.hasPermission("lurienstaff.warn")) {
            if(args.length == 1)
                return filterSuggestions(Arrays.stream(Bukkit.getOfflinePlayers()).map(OfflinePlayer::getName).toList(), args[0]);
            else if(args.length == 2)
                return Arrays.stream(WarnReason.values()).map(WarnReason::getCapitalizeName).toList();
        }
        return List.of();
    }
}
