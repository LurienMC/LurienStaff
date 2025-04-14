package dev.lurien.staff.lurienStaff.utils.gui;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public record GUIItem(ItemStack itemStack, List<Integer> slots) {
}
