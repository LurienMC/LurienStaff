package dev.lurien.staff.lurienStaff.configuration;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class DataConfig extends LurienConfiguration{

    public DataConfig(Plugin plugin) {
        super("data", plugin.getDataFolder(), plugin);
    }

    public long get(Player staff){
        return getConfig().getLong("StaffModeTotalSeconds."+staff.getName(), 0);
    }

    public void set(Player staff, long seconds) {
        getConfig().set("StaffModeTotalSeconds."+staff.getName(), seconds);
        save();
    }
}
