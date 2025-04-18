package dev.lurien.staff.lurienStaff.command;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface ISubCommand{

    String getName();
    boolean requierePermissions();
    @Nullable String getPermission();

    void execute(CommandSender s, String[] args);
    List<String> tabComplete(CommandSender s, String[] args, String line);
}
