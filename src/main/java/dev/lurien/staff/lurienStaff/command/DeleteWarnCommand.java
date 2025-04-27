package dev.lurien.staff.lurienStaff.command;

import dev.lurien.staff.lurienStaff.managers.WarnsManager;
import dev.lurien.staff.lurienStaff.model.Warn;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static dev.lurien.staff.lurienStaff.utils.MessagesUtils.sendMessageWithPrefix;

public class DeleteWarnCommand implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if(!sender.hasPermission("lurienstaff.delwarn")){
            sendMessageWithPrefix(sender, "&c⚠ No tienes permisos.");
            return false;
        }

        if(args.length < 2){
            sendMessageWithPrefix(sender, "&6&l! &6Usa: /delwarn <jugador> <id>");
            return false;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);

        if(!target.hasPlayedBefore()){
            sendMessageWithPrefix(sender, "&c⚠ El jugador "+args[0]+ " nunca ha entrado al servidor.");
            return false;
        }

        List<Warn> playerWarns = new ArrayList<>(WarnsManager.getPlayerWarns(target.getName()));
        playerWarns.sort(Comparator.comparing(Warn::getDate));

        int id;

        try{
            id = Integer.parseInt(args[1]);
        }catch (NumberFormatException e){
            sendMessageWithPrefix(sender, "&c⚠ El identificador ingresado es inválido.");
            return false;
        }

        if((id + 1) > playerWarns.size()){
            sendMessageWithPrefix(sender, "&c⚠ No se encontró la advertencia.");
            return false;
        }

        Warn warn = playerWarns.get(id);
        if(warn != null){
            WarnsManager.removeWarn(warn, sender);
            sendMessageWithPrefix(sender, "&a&l✔ &aHas removido la advertencia #"+id+" de "+target.getName()+".");
            return true;
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if(sender.hasPermission("lurienstaff.delwarn")) {
            if(args.length == 1){
                return Arrays.stream(Bukkit.getOfflinePlayers()).map(OfflinePlayer::getName).filter(WarnsManager::hasWarns).toList();
            }
            else if(args.length == 2){
                String name = args[0];
                List<String> l = new ArrayList<>();
                for (int i = 0; i < WarnsManager.getPlayerWarns(name).size(); i++) {
                    l.add(i+"");
                }
                return l;
            }
        }
        return List.of();
    }
}
