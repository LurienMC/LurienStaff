package dev.lurien.staff.lurienStaff.command;

import dev.lurien.staff.lurienStaff.managers.StaffModeManager;
import dev.lurien.staff.lurienStaff.utils.MessagesUtils;
import org.bukkit.Bukkit;
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
                if(!p.hasPermission("lurienstaff.headstaff"))
                    sendMessageWithPrefix(sender, "&6&l! &6Usa: /staffmode activado | desactivado");
                else
                    sendMessageWithPrefix(sender, "&6&l! &6Usa: /staffmode activado | desactivado [jugador]");
                return false;
            }

            if(args[0].equalsIgnoreCase("activado")){
                 if(args.length == 2 && p.hasPermission("lurienstaff.headstaff")){
                    Player t = Bukkit.getPlayerExact(args[1]);
                    if(t == null || !t.isOnline()){
                        sendMessageWithPrefix(sender, "&c⚠ Ese staff no esta conectado.");
                        return false;
                    }

                    if(!t.hasPermission("lurienstaff.staffmode")){
                        sendMessageWithPrefix(sender, "&c⚠ El jugador "+t.getName()+" no tiene permisos.");
                        return false;
                    }

                     if(StaffModeManager.isEnabled(t)){
                         sendMessageWithPrefix(sender, "&c⚠ El staff "+t.getName()+" ya activó el modo staff.");
                         return false;
                     }

                     sendMessageWithPrefix(t, Arrays.asList("&a&l✔ &aActivaste el modo staff.",
                             " ",
                             "&7Created by @octdamfar",
                             " "));
                     StaffModeManager.setEnable(t);
                     return true;
                }

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

                if(args.length == 2 && p.hasPermission("lurienstaff.headstaff")){
                    Player t = Bukkit.getPlayerExact(args[1]);
                    if(t == null || !t.isOnline()){
                        sendMessageWithPrefix(sender, "&c⚠ Ese staff no esta conectado.");
                        return false;
                    }

                    if(!t.hasPermission("lurienstaff.staffmode")){
                        sendMessageWithPrefix(sender, "&c⚠ El jugador "+t.getName()+" no tiene permisos.");
                        return false;
                    }

                    if(!StaffModeManager.isEnabled(t)){
                        sendMessageWithPrefix(sender, "&c⚠ El staff "+t.getName()+" no tiene activado el modo staff.");
                        return false;
                    }

                    sendMessageWithPrefix(t, Arrays.asList("&a&l✖ &aDesactivaste el modo staff.",
                            " ",
                            "&7Created by @octdamfar",
                            " "));
                    StaffModeManager.setDisable(t, false);
                    return true;
                }

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
