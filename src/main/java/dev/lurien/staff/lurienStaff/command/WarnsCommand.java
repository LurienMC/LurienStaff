package dev.lurien.staff.lurienStaff.command;

import dev.lurien.staff.lurienStaff.managers.WarnsManager;
import dev.lurien.staff.lurienStaff.model.Warn;
import dev.lurien.staff.lurienStaff.model.WarnReason;
import dev.lurien.staff.lurienStaff.utils.MessagesUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.format.DateTimeFormatter;
import java.util.*;

import static dev.lurien.staff.lurienStaff.utils.MessagesUtils.sendMessageWithPrefix;

public class WarnsCommand implements TabExecutor {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if (args.length == 0) {
            // Mostrar los warns del propio jugador
            if (!WarnsManager.hasWarns(sender.getName())) {
                sendMessageWithPrefix(sender, "&6&l! &6No tienes ninguna advertencia.");
                return true;
            }

            showWarns(sender, sender.getName(), true);
            return true;

        } else if (args.length == 1) {
            if (!sender.hasPermission("lurienstaff.warns.others")) {
                sendMessageWithPrefix(sender, "&cNo tienes permiso para ver advertencias de otros jugadores.");
                return true;
            }

            OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);

            if (!WarnsManager.hasWarns(target.getName())) {
                sendMessageWithPrefix(sender, "&aEse jugador no tiene advertencias.");
                return true;
            }

            showWarns(sender, target.getName(), false);
            return true;
        }

        sendMessageWithPrefix(sender, "&cUso: /warns [jugador]");
        return true;
    }

    private void showWarns(CommandSender sender, String targetName, boolean self) {
        Map<String, Integer> reasonCount = new HashMap<>();

        List<Warn> playerWarns = new ArrayList<>(WarnsManager.getPlayerWarns(targetName));
        playerWarns.sort(Comparator.comparing(Warn::getDate));
        
        sendMessageWithPrefix(sender, (self ? "&e&l! &fTus advertencias:" : "&e&l! &fAdvertencias de &e" + targetName + "&f:"));
        for (Warn warn : playerWarns) {
            String staff = warn.getStaff();
            String reason = warn.getReason();
            WarnReason wr = WarnReason.getWarnByName(reason);
            String prettyReason = wr != null ? wr.getCapitalizeName() : reason;
            int max = wr != null ? wr.getMaxWarnings() : 1;

            int count = reasonCount.getOrDefault(prettyReason.toLowerCase(), 0) + 1;
            reasonCount.put(prettyReason.toLowerCase(), count);

            String fecha = FORMATTER.format(warn.getDate());

            sendMessageWithPrefix(sender, "&fStaff: &e" + staff + " &f- Fecha: &a" + fecha + " &f- Razón: &b" + prettyReason + " &7(" + count + "/" + max + ")");
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if (args.length == 1 && sender.hasPermission("lurienstaff.warns.others")) {
            return MessagesUtils.filterSuggestions(Arrays.stream(Bukkit.getOfflinePlayers()).map(OfflinePlayer::getName).toList(), args[0]);
        }
        return List.of();
    }

}
