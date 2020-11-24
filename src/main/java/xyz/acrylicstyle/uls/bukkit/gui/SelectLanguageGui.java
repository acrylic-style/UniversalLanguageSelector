package xyz.acrylicstyle.uls.bukkit.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import xyz.acrylicstyle.tomeito_api.gui.PlayerGui;
import xyz.acrylicstyle.uls.api.Language;
import xyz.acrylicstyle.uls.api.UniversalLanguageSelectorAPIProvider;
import xyz.acrylicstyle.uls.api.util.LanguageHolder;
import xyz.acrylicstyle.uls.api.util.LanguageKeys;
import xyz.acrylicstyle.uls.bukkit.util.Util;

public class SelectLanguageGui implements InventoryHolder, PlayerGui, Listener {
    private final JavaPlugin plugin;

    public SelectLanguageGui(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void register() {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public Inventory getInventory(String title) {
        Inventory inventory = Bukkit.createInventory(this, 27, title);
        inventory.setItem(11, Util.displayName(Util.getWool(DyeColor.RED), ChatColor.RED + "日本語"));
        inventory.setItem(13, Util.displayName(Util.getWool(DyeColor.WHITE), ChatColor.WHITE + "English"));
        inventory.setItem(15, Util.displayName(Util.getWool(DyeColor.RED), ChatColor.RED + "中文"));
        return inventory;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return getInventory("");
    }

    @EventHandler
    public void onInventoryClick(@NotNull InventoryClickEvent e) {
        if (e.getInventory().getHolder() != this) return;
        e.setCancelled(true);
        if (e.getSlot() == 11) {
            UniversalLanguageSelectorAPIProvider.getAPI().bukkit().setLanguage((Player) e.getWhoClicked(), Language.JAPANESE).then(v -> {
                changed(e.getWhoClicked(), "日本語");
                return null;
            }).queue();
        } else if (e.getSlot() == 13) {
            UniversalLanguageSelectorAPIProvider.getAPI().bukkit().setLanguage((Player) e.getWhoClicked(), Language.ENGLISH).then(v -> {
                changed(e.getWhoClicked(), "English");
                return null;
            }).queue();
        } else if (e.getSlot() == 15) {
            UniversalLanguageSelectorAPIProvider.getAPI().bukkit().setLanguage((Player) e.getWhoClicked(), Language.CHINESE).then(v -> {
                changed(e.getWhoClicked(), "中文");
                return null;
            }).queue();
        }
    }

    private void changed(HumanEntity player, String language) {
        player.closeInventory();
        LanguageHolder.instance().get(LanguageKeys.GUI_CHANGED_LANGUAGE, player.getUniqueId()).then(message -> {
            player.sendMessage(ChatColor.GREEN + String.format(message, ChatColor.YELLOW + language + ChatColor.GREEN));
            return null;
        }).queue();
    }

    @EventHandler
    public void onInventoryDrag(@NotNull InventoryDragEvent e) {
        if (e.getInventory().getHolder() != this) return;
        e.setCancelled(true);
    }
}
