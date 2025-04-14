package dev.lurien.staff.lurienStaff.command;

import dev.lurien.staff.lurienStaff.managers.StaffModeManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

import static dev.lurien.staff.lurienStaff.utils.MessagesUtils.sendMessageWithPrefix;

public class StaffModeTopCommand implements TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if(!sender.hasPermission("lurienstaff.staffmode.top")){
            sendMessageWithPrefix(sender, "&c⚠ No tienes permisos.");
            return false;
        }

        if(args.length == 0){
            sendMessageWithPrefix(sender, "&6&l! &6Usa: /staffmodetop <límite>");
            return false;
        }

        int limit;

        try{
            limit = Integer.parseInt(args[0]);
        }catch (NumberFormatException e){
            sendMessageWithPrefix(sender, "&c⚠ La entrada numérica es inválida.");
            return false;
        }

        Map<String, String> top = StaffModeManager.getTopStaffsMode(limit);

        if(top.isEmpty()){
            sendMessageWithPrefix(sender, "&6&l! &6El top está vacio.");
            return false;
        }

        sendMessageWithPrefix(sender, "&e&l! &eTop "+limit+" staffs con más tiempo en servicio:");
        int i = 1;
        for (Map.Entry<String, String> entry : top.entrySet()) {
            sendMessageWithPrefix(sender, " &b#"+i+" &f- &a"+entry.getKey()+" &f- &e"+entry.getValue());
            i++;
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if(sender.hasPermission("lurienstaff.staffmode.top") && args.length == 1){
            return List.of("5", "10", "15", "20", "50", "100");
        }
        return List.of();
    }
}
