package dev.lurien.staff.lurienStaff.command;

import dev.lurien.staff.lurienStaff.LurienStaff;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static dev.lurien.staff.lurienStaff.utils.MessagesUtils.filterSuggestions;
import static dev.lurien.staff.lurienStaff.utils.MessagesUtils.sendMessageWithPrefix;

public class StaffAdminCommand implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if(!sender.hasPermission("lurienstaff.admin")){
            sendMessageWithPrefix(sender, "&c⚠ No tienes permisos.");
            return false;
        }

        if(args.length == 0){
            sendMessageWithPrefix(sender, "&6&l! &6Usa: /sa reload");
            return false;
        }

        if(args[0].equals("reload")){
            long var = System.currentTimeMillis();
            LurienStaff.getModerationConfig().loadConfig();
            long var1 = System.currentTimeMillis() - var;
            sendMessageWithPrefix(sender, "&a&l✔ &aConfiguración recargada en "+var1+"ms.");
        }else{
            sendMessageWithPrefix(sender, "&6&l! &6Argumento incorrecto. Usa: /sa reload");
            return false;
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if(args.length == 1 && sender.hasPermission("lurienstaff.staffadmin"))
            return filterSuggestions(List.of("reload"), args[0]);

        return List.of();
    }
}
