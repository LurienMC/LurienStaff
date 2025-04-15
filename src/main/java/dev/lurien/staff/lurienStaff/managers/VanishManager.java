package dev.lurien.staff.lurienStaff.managers;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import dev.lurien.staff.lurienStaff.LurienStaff;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;

import java.util.HashSet;
import java.util.Set;

import static dev.lurien.staff.lurienStaff.LurienStaff.sendWebhook;
import static dev.lurien.staff.lurienStaff.utils.MessagesUtils.logAdmins;

@SuppressWarnings("unchecked")
public class VanishManager {

    public static final Set<String> playersInVanish = new HashSet<>();
    private static final byte GLOW_MASK = 0x40;

    public static boolean isInVanish(Player p){
        return playersInVanish.contains(p.getName());
    }

    public static void setVanish(Player p, boolean enable){
        if(enable){
            playersInVanish.add(p.getName());
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if(onlinePlayer.hasPermission("lurienstaff.vanish")){
                    setGlowing(p, true, onlinePlayer);
                }else{
                    onlinePlayer.hidePlayer(LurienStaff.getPlugin(LurienStaff.class), p);
                }
            }

            logAdmins("&c&l! &fEl staff &6"+p.getName()+"&f está en vanish.", p);

            JSONObject embed = new JSONObject();
            embed.put("title", p.getName()+" está en vanish");
            embed.put("color", 0xFC3232);
            embed.put("description", "\n\n__Staff de LurienMC__");

            JSONObject thumbnail = new JSONObject();
            thumbnail.put("url", "https://visage.surgeplay.com/full/"+p.getName());
            embed.put("thumbnail", thumbnail);

            sendWebhook(embed);
        } else {
            playersInVanish.remove(p.getName());
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                setGlowing(p, false, onlinePlayer);
                onlinePlayer.showPlayer(LurienStaff.getPlugin(LurienStaff.class), p);
            }

            logAdmins("&c&l! &fEl staff &6"+p.getName()+"&f ya no está en vanish.", p);

            JSONObject embed = new JSONObject();
            embed.put("title", p.getName()+" ya no está en vanish");
            embed.put("color", 0xDB2727);
            embed.put("description", "\n\n__Staff de LurienMC__");

            JSONObject thumbnail = new JSONObject();
            thumbnail.put("url", "https://visage.surgeplay.com/full/"+p.getName());
            embed.put("thumbnail", thumbnail);

            sendWebhook(embed);
        }
    }

    public static void setGlowing(Player target, boolean glowing, Player viewer) {
        try {
            WrappedDataWatcher watcher = new WrappedDataWatcher();
            WrappedDataWatcher.Serializer serializer = WrappedDataWatcher.Registry.get(Byte.class);

            byte flags = 0x00;

            WrappedDataWatcher original = WrappedDataWatcher.getEntityWatcher(target);
            Byte currentFlags = original.getByte(0);
            if (currentFlags != null) {
                flags = currentFlags;
            }

            if (glowing) {
                flags |= GLOW_MASK;
            } else {
                flags &= ~GLOW_MASK;
            }

            watcher.setEntity(target);
            watcher.setObject(0, serializer, flags);

            PacketContainer packet = ProtocolLibrary.getProtocolManager()
                    .createPacket(com.comphenix.protocol.PacketType.Play.Server.ENTITY_METADATA);

            packet.getIntegers().write(0, target.getEntityId());
            packet.getWatchableCollectionModifier().write(0, watcher.getWatchableObjects());

            ProtocolLibrary.getProtocolManager().sendServerPacket(viewer, packet);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
