package dev.lurien.staff.lurienStaff.managers;

import dev.lurien.bot.LurienBot;
import dev.lurien.staff.lurienStaff.LurienStaff;
import lombok.Setter;
import me.clip.placeholderapi.PlaceholderAPI;
import net.dv8tion.jda.api.EmbedBuilder;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashSet;
import java.util.Set;

import static dev.lurien.staff.lurienStaff.LurienStaff.sendWebhookActivity;
import static dev.lurien.staff.lurienStaff.utils.MessagesUtils.*;

@SuppressWarnings("unchecked")
public class VanishManager {

    public static final Set<String> playersInVanish = new HashSet<>();
    @Setter
    private static Scoreboard scoreboard;

    public static boolean isInVanish(Player p){
        return playersInVanish.contains(p.getName());
    }

    public static void setVanish(Player p, boolean enable){
        if(enable){
            playersInVanish.add(p.getName());
            applyGlow(p);
            broadcast(PlaceholderAPI.setPlaceholders(p, "&c>>> &l- &f%vault_prefix% "+p.getName()+" &fsalió del servidor."));
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if(!onlinePlayer.hasPermission("lurienstaff.vanish")){
                    onlinePlayer.hidePlayer(LurienStaff.getPlugin(LurienStaff.class), p);
                }
            }

            logAdmins("&c&l! &fEl staff &c"+p.getName()+"&f está en vanish.", p);

            EmbedBuilder embed = new EmbedBuilder().setTitle(p.getName()+" ya no está en vanish")
                    .setColor(0xFC3232)
                    .setDescription("\n\n__Staff de LurienMC__")
                    .setThumbnail("https://visage.surgeplay.com/full/"+p.getName())
                    .setFooter("Created by @octdamfar", LurienBot.getGuild().getIconUrl());

            sendWebhookActivity(embed);
        } else {
            playersInVanish.remove(p.getName());
            removeGlow(p);
            broadcast(PlaceholderAPI.setPlaceholders(p, "&e>>> &l+ &f%vault_prefix% "+p.getName()+" &fentró al servidor."));
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                onlinePlayer.showPlayer(LurienStaff.getPlugin(LurienStaff.class), p);
            }

            logAdmins("&c&l! &fEl staff &c"+p.getName()+"&f ya no está en vanish.", p);

            EmbedBuilder embed = new EmbedBuilder().setTitle(p.getName()+" ya no está en vanish")
                    .setColor(0xDB2727)
                    .setDescription("\n\n__Staff de LurienMC__")
                    .setThumbnail("https://visage.surgeplay.com/full/"+p.getName())
                    .setFooter("Created by @octdamfar", LurienBot.getGuild().getIconUrl());

            sendWebhookActivity(embed);
        }
    }

    public static void applyGlow(Player target) {
        Team team = scoreboard.getTeam("vanish_glow");
        if (team == null) return;

        team.addEntry(target.getName());
        target.setGlowing(true);
    }

    private static void removeGlow(Player target) {
        Team team = scoreboard.getTeam("vanish_glow");
        if (team != null) {
            team.removeEntry(target.getName());
        }

        target.setGlowing(false);
    }

    public static void setupTeam() {
        Team team = scoreboard.getTeam("vanish_glow");
        if (team == null) {
            team = scoreboard.registerNewTeam("vanish_glow");
        }

        team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER);
        team.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER);
        team.color(NamedTextColor.WHITE);
    }
}
