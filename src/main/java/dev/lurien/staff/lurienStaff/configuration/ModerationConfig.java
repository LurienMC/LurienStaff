package dev.lurien.staff.lurienStaff.configuration;

import dev.lurien.staff.lurienStaff.managers.ModerationManager;
import org.bukkit.plugin.Plugin;

public class ModerationConfig extends LurienConfiguration {

    public ModerationConfig(Plugin plugin) {
        super("moderation", plugin.getDataFolder(), plugin);
    }

    public void loadConfig(){
        ModerationManager.getBadWords().clear();
        ModerationManager.getBadWords().addAll(getConfig().getStringList("groserías"));
        ModerationManager.getBypassBadWords().clear();
        ModerationManager.getBypassBadWords().addAll(getConfig().getStringList("bypass-groserías"));
        ModerationManager.getDiscriminationWords().clear();
        ModerationManager.getDiscriminationWords().addAll(getConfig().getStringList("discriminación"));
        ModerationManager.getInappropriateBehaviorWords().clear();
        ModerationManager.getInappropriateBehaviorWords().addAll(getConfig().getStringList("comportamiento"));
    }
}
