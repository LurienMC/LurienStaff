package dev.lurien.staff.lurienStaff.command;

import dev.lurien.staff.lurienStaff.managers.StaffModeManager;
import dev.lurien.staff.lurienStaff.utils.MessagesUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

import static dev.lurien.staff.lurienStaff.utils.MessagesUtils.sendMessageWithPrefix;

public class StaffModeCommand implements TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if(sender instanceof Player p){
            if(!p.hasPermission("lurienstaff.staffmode")){
                sendMessageWithPrefix(sender, "&c⚠ No tienes permisos.");
                return false;
            }

            if(args.length == 0){
                sendMessageWithPrefix(sender, "&6&l! &6Usa: /staffmode activado | desactivado");
                return false;
            }

            if(args[0].equalsIgnoreCase("activado")){
                if(StaffModeManager.isEnabled(p)){
                    sendMessageWithPrefix(sender, "&c⚠ Ya activaste el modo staff.");
                    return false;
                }

                sendMessageWithPrefix(sender, Arrays.asList("&a&l✔ &aActivaste el modo staff.",
                        " ",
                        "&7Created by @octdamfar",
                        " "));
                StaffModeManager.setEnable(p);
                return true;
            }else if(args[0].equalsIgnoreCase("desactivado")){
                if(!StaffModeManager.isEnabled(p)){
                    sendMessageWithPrefix(sender, "&c⚠ No tienes activo el modo staff.");
                    return false;
                }

                sendMessageWithPrefix(sender, Arrays.asList("&a&l✖ &aDesactivaste el modo staff.",
                        " ",
                        "&7Created by @octdamfar",
                        " "));
                StaffModeManager.setDisable(p, false);
                return true;
            }else{
                sendMessageWithPrefix(sender, "&6&l! &6Argumento incorrecto. Usa: /staffmode activado | desactivado");
                return false;
            }

        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if(sender.hasPermission("lurienstaff.staffmode") && args.length == 1){
            return MessagesUtils.filterSuggestions(List.of("activado", "desactivado"), args[0]);
        }

        return List.of();
    }
}
