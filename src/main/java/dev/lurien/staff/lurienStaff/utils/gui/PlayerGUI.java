package dev.lurien.staff.lurienStaff.utils.gui;

import lombok.Data;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

@Data
public class PlayerGUI implements InventoryHolder {

    private final ServerGUI gui;
    private final Player viewer;
    private Inventory inventory;

    public void open(){
        inventory = gui.build(this);
        viewer.openInventory(inventory);
    }

    public void refresh() {
        inventory.clear();
        for (GUIButton button : gui.getButtons()) {
            for (Integer i : button.slot()) {
                inventory.setItem(i, button.itemStack());
            }
        }
    }
}
