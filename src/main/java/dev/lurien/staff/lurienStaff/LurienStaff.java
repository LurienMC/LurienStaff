package dev.lurien.staff.lurienStaff;

import dev.lurien.staff.lurienStaff.command.StaffModeCommand;
import dev.lurien.staff.lurienStaff.command.StaffModeTopCommand;
import dev.lurien.staff.lurienStaff.command.TpHereCommand;
import dev.lurien.staff.lurienStaff.command.TpRandomPlayerCommand;
import dev.lurien.staff.lurienStaff.configuration.DataConfig;
import dev.lurien.staff.lurienStaff.utils.ServerVersion;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;

public final class LurienStaff extends JavaPlugin {

    @Getter
    private static ServerVersion serverVersion;
    private static final String webhookUrl = "https://discord.com/api/webhooks/1361180251044053166/WQ-VMVM17uybm6ykNsX21GOKtqVW2bXs8IoVsleNlmtCjZQxtxKW6Gkk_gzXloz8c7Co";
    @Getter
    private static DataConfig data;

    @Override
    public void onEnable() {
        String packageName = Bukkit.getServer().getClass().getPackage().getName();
        String bukkitVersion = Bukkit.getServer().getBukkitVersion().split("-")[0];

        switch(bukkitVersion){
            case "1.20.5":
            case "1.20.6":
                serverVersion = ServerVersion.v1_20_R4;
                break;
            case "1.21":
            case "1.21.1":
                serverVersion = ServerVersion.v1_21_R1;
                break;
            case "1.21.2":
            case "1.21.3":
                serverVersion = ServerVersion.v1_21_R2;
                break;
            case "1.21.4":
                serverVersion = ServerVersion.v1_21_R3;
                break;
            default:
                serverVersion = ServerVersion.valueOf(packageName.replace("org.bukkit.craftbukkit.", ""));
        }

        data = new DataConfig(this);

        Objects.requireNonNull(getCommand("staffmode")).setExecutor(new StaffModeCommand());
        Objects.requireNonNull(getCommand("staffmode")).setTabCompleter(new StaffModeCommand());
        Objects.requireNonNull(getCommand("staffmodetop")).setExecutor(new StaffModeTopCommand());
        Objects.requireNonNull(getCommand("staffmodetop")).setTabCompleter(new StaffModeTopCommand());
        Objects.requireNonNull(getCommand("tphere")).setExecutor(new TpHereCommand());
        Objects.requireNonNull(getCommand("tphere")).setTabCompleter(new TpHereCommand());
        Objects.requireNonNull(getCommand("tprandomplayer")).setExecutor(new TpRandomPlayerCommand());
        Objects.requireNonNull(getCommand("tprandomplayer")).setTabCompleter(new TpRandomPlayerCommand());
    }

    @Override
    public void onDisable() {

    }

    @SuppressWarnings("unchecked")
    public static void sendWebhook(JSONObject embed) {
        try {
            JSONArray embedsArray = new JSONArray();
            embedsArray.put(embed);

            JSONObject json = new JSONObject();
            json.put("embeds", embedsArray);
            json.put("username", "Lurien - Staff - Registros");
            json.put("avatar_url", "https://media.discordapp.net/attachments/1320172178469027932/1361184687497805945/logo.png?ex=67fdd587&is=67fc8407&hm=e58cc6f503625da4ac30cca9e2316ba35b0626e7d236b178b2ae652d216da93a&=&format=webp&quality=lossless&width=350&height=350");

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(webhookUrl))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                    .build();

            client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
