package xyz.acrylicstyle.uls;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import xyz.acrylicstyle.tomeito_api.providers.ConfigProvider;
import xyz.acrylicstyle.uls.event.LanguageChangedEvent;
import xyz.acrylicstyle.uls.listener.EventListener;

import java.util.Locale;

public class UniversalLanguageSelector extends JavaPlugin {
    private static final ConfigProvider config = new ConfigProvider("./plugins/UniversalLanguageSelector/config.yml");

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new EventListener(), this);
    }

    public static void setLocale(@NotNull Player player, @NotNull Locale locale) {
        LanguageChangedEvent event = new LanguageChangedEvent(player, locale, LanguageChangedEvent.Cause.PLUGIN);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return;
        config.setThenSave("players." + player.getUniqueId().toString(), locale.toLanguageTag());
    }

    @NotNull
    public static Locale getLocale(@NotNull Player player) {
        return Locale.forLanguageTag(config.getString("players." + player.getUniqueId().toString(), Locale.JAPANESE.toLanguageTag()));
    }
}
