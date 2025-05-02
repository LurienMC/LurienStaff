package dev.lurien.staff.lurienStaff.command;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static dev.lurien.staff.lurienStaff.listeners.StaffChatListener.webhook;
import static dev.lurien.staff.lurienStaff.utils.MessagesUtils.*;

public class StaffChatCommand implements TabExecutor {

    public static final Set<String> staffChatEnable = new HashSet<>();

    @SuppressWarnings("unchecked")
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if(!sender.hasPermission("lurienstaff.staffchat")){
            sendMessageWithPrefix(sender, "&c⚠ No tienes permisos.");
            return false;
        }

        if(args.length == 0 && sender instanceof Player player){
            if(staffChatEnable.contains(player.getName())){
                staffChatEnable.remove(player.getName());
                sendMessageWithPrefix(player, "&a&l✖ &aYa no estás en chat de staffs.");
                logAdmins("&e&l! &fEl staff &b"+player.getName()+" &fha salido del chat de staffs.", player);
            }else{
                staffChatEnable.add(player.getName());
                sendMessageWithPrefix(player, "&a&l✔ &aAhora estás en chat de staffs.");
                logAdmins("&e&l! &fEl staff &b"+player.getName()+" &fha entrado al chat de staffs.", player);
            }
            return true;
        }

        StringBuilder sb = new StringBuilder();

        for (@NotNull String arg : args) {
            sb.append(arg).append(" ");
        }

        String message = sb.toString().trim();
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if(onlinePlayer.hasPermission("lurienstaff.staffchat")){
                onlinePlayer.sendMessage(colorize("#37D7F6&lS#36CAF7&lt#35BDF8&la#34B0F9&lf#33A4FB&lf#3297FC&lC#318AFD&lh#307DFE&la#2F70FF&lt &f» "+ (sender instanceof Player p ? PlaceholderAPI.setPlaceholders(p, "%vault_prefix%") : "")+" "+sender.getName()+" &8» &7")+message);
            }
        }

        Bukkit.getConsoleSender().sendMessage(colorize("#37D7F6&lS#36CAF7&lt#35BDF8&la#34B0F9&lf#33A4FB&lf#3297FC&lC#318AFD&lh#307DFE&la#2F70FF&lt &f» "+ (sender instanceof Player p ? PlaceholderAPI.setPlaceholders(p, "%vault_prefix%") : "")+" "+sender.getName()+" &8» &7")+message);


        try {

            JSONObject json = new JSONObject();
            json.put("username", !(sender instanceof Player p) ? "Consola" : p.getName());
            json.put("avatar_url", !(sender instanceof Player p) ? "https://minotar.net/avatar/DamianFarias/100" : "https://minotar.net/avatar/"+p.getName()+"/100");
            json.put("content", message);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(webhook))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                    .build();

            client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (IOException | InterruptedException ex) {
            throw new RuntimeException(ex);
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        return List.of();
    }

}
