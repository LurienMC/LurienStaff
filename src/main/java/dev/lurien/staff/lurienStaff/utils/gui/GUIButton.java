package dev.lurien.staff.lurienStaff.utils.gui;

import lombok.Data;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.function.Consumer;

public record GUIButton(ItemStack itemStack, List<Integer> slot, Consumer<Player> consumer) {
}
