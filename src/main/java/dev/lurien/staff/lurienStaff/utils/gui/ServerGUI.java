package dev.lurien.staff.lurienStaff.utils.gui;

import dev.lurien.staff.lurienStaff.utils.MessagesUtils;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("deprecation")
@Data

public abstract class ServerGUI {

    private final String title;
    private final int rows;
    private final List<GUIButton> buttons;

    public Inventory build(PlayerGUI pg){
        Inventory inv = Bukkit.createInventory(pg, rows * 9, MessagesUtils.colorize(title));

        for (GUIButton button : buttons) {
            for (Integer i : button.slot()) {
                inv.setItem(i, button.itemStack());
            }
        }

        return inv;
    }

    public void addItem(GUIButton gb){
        buttons.add(gb);
    }

    public void addItem(ItemStack item, List<Integer> slots, Consumer<Player> consumer){
        buttons.add(new GUIButton(item, slots, consumer));
    }

    public void addItem(ItemStack item, int slot, Consumer<Player> consumer){
        buttons.add(new GUIButton(item, List.of(slot), consumer));
    }
}
