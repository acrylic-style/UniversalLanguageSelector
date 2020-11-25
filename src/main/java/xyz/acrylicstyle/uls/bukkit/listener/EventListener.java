package xyz.acrylicstyle.uls.bukkit.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import xyz.acrylicstyle.nmsapi.v1_8_8.craftbukkit.entity.CraftPlayer;
import xyz.acrylicstyle.uls.api.Language;
import xyz.acrylicstyle.uls.api.util.LanguageHolder;
import xyz.acrylicstyle.uls.api.util.LanguageKeys;
import xyz.acrylicstyle.uls.bukkit.UniversalLanguageSelectorPlugin;

public class EventListener implements Listener {
    private final UniversalLanguageSelectorPlugin plugin;

    public EventListener(UniversalLanguageSelectorPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Bukkit.getScheduler().runTaskLater(this.plugin, () -> this.plugin.db.language.has(e.getPlayer().getUniqueId()).then(has -> {
            if (has) return null;
            String locale = CraftPlayer.getInstance(e.getPlayer()).getHandle().getLocale();
            if (locale == null) return null;
            Language language;
            if (locale.equalsIgnoreCase("en_us")) {
                language = Language.ENGLISH;
            } else if (locale.equalsIgnoreCase("ja_jp")) {
                language = Language.JAPANESE;
            } else if (locale.equalsIgnoreCase("zh_tw") || locale.equalsIgnoreCase("zh_cn")) {
                language = Language.CHINESE;
            } else {
                language = Language.ENGLISH;
            }
            this.plugin.db.language.set(e.getPlayer().getUniqueId(), language).queue();
            e.getPlayer().sendMessage(ChatColor.GOLD + "==============================");
            e.getPlayer().sendMessage("");
            e.getPlayer().sendMessage(LanguageHolder.instance().get(LanguageKeys.SET_LANGUAGE_AUTOMATICALLY, language));
            if (language != Language.ENGLISH) e.getPlayer().sendMessage(ChatColor.GREEN + "Your language was set to " + ChatColor.GOLD + language.name() + ChatColor.GREEN + ". Please do " + ChatColor.YELLOW + "/lang" + ChatColor.GREEN + " if it was wrong language.");
            e.getPlayer().sendMessage("");
            e.getPlayer().sendMessage(ChatColor.GOLD + "==============================");
            return null;
        }).queue(), 20);
    }
}
