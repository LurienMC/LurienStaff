package dev.lurien.staff.lurienStaff.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Data
@AllArgsConstructor
public class Warn {

    @NotNull
    private final OfflinePlayer player;
    private String reason;
    @Nullable
    private WarnReason warnReason;

    public boolean isStaffPlayer() {
        return Bukkit.getOfflinePlayer(staff).hasPlayedBefore();
    }
    private String staff;

    public OfflinePlayer getStaffPlayer() {
        return Bukkit.getOfflinePlayer(staff);
    }
}
