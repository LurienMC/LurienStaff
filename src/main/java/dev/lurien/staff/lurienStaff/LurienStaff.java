package dev.lurien.staff.lurienStaff;

import dev.lurien.bot.LurienBot;
import dev.lurien.staff.lurienStaff.command.*;
import dev.lurien.staff.lurienStaff.configuration.DataConfig;
import dev.lurien.staff.lurienStaff.configuration.ModerationConfig;
import dev.lurien.staff.lurienStaff.listeners.ModerationListener;
import dev.lurien.staff.lurienStaff.listeners.StaffChatListener;
import dev.lurien.staff.lurienStaff.listeners.StaffModeListener;
import dev.lurien.staff.lurienStaff.listeners.VanishListener;
import dev.lurien.staff.lurienStaff.managers.VanishManager;
import dev.lurien.staff.lurienStaff.managers.WarnsManager;
import dev.lurien.staff.lurienStaff.utils.ServerVersion;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.NewsChannel;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class LurienStaff extends JavaPlugin {

    @Getter
    private static ServerVersion serverVersion;
    @Getter
    @Setter
    private static NewsChannel staffModeChannel, activityChannel, chatChannel, moderationLogsChannel;
    @Getter
    private static DataConfig dataConfig;
    @Getter
    private static ModerationConfig moderationConfig;

    @Override
    public void onEnable() {
        setVersion();
        VanishManager.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
        VanishManager.setupTeam();

        Dotenv dotenv = Dotenv.configure()
                .directory(getDataFolder().getAbsolutePath())
                .filename(".env")
                .load();

        StaffChatListener.webhook = dotenv.get("STAFF_C_WH");

        dataConfig = new DataConfig(this);
        WarnsManager.load();
        moderationConfig = new ModerationConfig(this);
        moderationConfig.loadConfig();
        
        registerCommands();
        registerListeners();
    }

    @Override
    public void onDisable() {
        WarnsManager.saveAll();
    }


    @SuppressWarnings("DataFlowIssue")
    private void registerCommands() {
        getCommand("staffmode").setExecutor(new StaffModeCommand());
        getCommand("staffmode").setTabCompleter(new StaffModeCommand());
        getCommand("staffmodetop").setExecutor(new StaffModeTopCommand());
        getCommand("staffmodetop").setTabCompleter(new StaffModeTopCommand());
        getCommand("tphere").setExecutor(new TpHereCommand());
        getCommand("tphere").setTabCompleter(new TpHereCommand());
        getCommand("tprandomplayer").setExecutor(new TpRandomPlayerCommand());
        getCommand("tprandomplayer").setTabCompleter(new TpRandomPlayerCommand());
        getCommand("stafflist").setExecutor(new StaffListCommand());
        getCommand("stafflist").setTabCompleter(new StaffListCommand());
        getCommand("staffchat").setExecutor(new StaffChatCommand());
        getCommand("staffchat").setTabCompleter(new StaffChatCommand());
        getCommand("vanish").setExecutor(new VanishCommand());
        getCommand("vanish").setTabCompleter(new VanishCommand());
        getCommand("staffadmin").setExecutor(new StaffAdminCommand());
        getCommand("staffadmin").setTabCompleter(new StaffAdminCommand());
        getCommand("warn").setExecutor(new WarnCommand());
        getCommand("warn").setTabCompleter(new WarnCommand());
    }

    public void registerListeners(){
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new StaffModeListener(), this);
        pm.registerEvents(new StaffChatListener(), this);
        pm.registerEvents(new VanishListener(), this);
        pm.registerEvents(new ModerationListener(), this);
    }

    private void setVersion() {
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
    }

    public static void sendWebhookStaffMode(EmbedBuilder embed) {
        staffModeChannel.sendMessageEmbeds(embed.build()).queue();
    }

    public static void sendWebhookActivity(EmbedBuilder embed) {
        activityChannel.sendMessageEmbeds(embed.build()).queue();
    }

    public static void sendWebhookChat(EmbedBuilder embed) {
        chatChannel.sendMessageEmbeds(embed.build()).queue();
    }
}
