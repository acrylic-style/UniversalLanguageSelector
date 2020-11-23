package xyz.acrylicstyle.uls.bukkit.util;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

public class Util {
    @NotNull
    public static ItemStack getPlayerHead(@NotNull String name) {
        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1);
        item.setDurability((short) 3);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwner(name);
        item.setItemMeta(meta);
        return item;
    }

    @SuppressWarnings("deprecation")
    @NotNull
    public static ItemStack getWool(@NotNull DyeColor color) {
        ItemStack item = new ItemStack(Material.WOOL);
        item.setDurability(color.getWoolData());
        return item;
    }

    @NotNull
    public static ItemStack displayName(@NotNull ItemStack item, @NotNull String displayName) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
        item.setItemMeta(meta);
        return item;
    }
}
