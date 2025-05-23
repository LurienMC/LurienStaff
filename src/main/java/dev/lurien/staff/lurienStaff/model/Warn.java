package dev.lurien.staff.lurienStaff.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Warn {

    @NotNull
    private final OfflinePlayer player;
    private UUID id;
    private String reason;
    @Nullable
    private WarnReason warnReason;
    private LocalDateTime date;

    public boolean isStaffPlayer() {
        return Bukkit.getOfflinePlayer(staff).hasPlayedBefore();
    }
    private String staff;

    public OfflinePlayer getStaffPlayer() {
        return Bukkit.getOfflinePlayer(staff);
    }
}
