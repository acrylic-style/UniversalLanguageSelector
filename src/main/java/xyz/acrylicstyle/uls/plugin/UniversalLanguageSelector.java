package xyz.acrylicstyle.uls.plugin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import util.ActionableResult;
import xyz.acrylicstyle.tomeito_api.providers.ConfigProvider;
import xyz.acrylicstyle.tomeito_api.utils.Log;
import xyz.acrylicstyle.uls.api.Language;
import xyz.acrylicstyle.uls.api.UniversalLanguageSelectorAPI;
import xyz.acrylicstyle.uls.api.UniversalLanguageSelectorAPIProvider;
import xyz.acrylicstyle.uls.api.sql.LanguageDataAPI;
import xyz.acrylicstyle.uls.event.LanguageChangeEvent;
import xyz.acrylicstyle.uls.plugin.listener.*;
import xyz.acrylicstyle.uls.plugin.sql.ConnectionHolder;

import java.sql.SQLException;

public class UniversalLanguageSelector extends JavaPlugin implements UniversalLanguageSelectorAPI {
    public final ConfigProvider config = new ConfigProvider("./plugins/UniversalLanguageSelector/config.yml");
    public ConnectionHolder db;
    public final Log.Logger logger = Log.of("UniversalLanguageSelector");

    @Override
    public void onLoad() {
        UniversalLanguageSelectorAPIProvider.setAPI(this);
    }

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new EventListener(), this);
        String host = config.getString("database.host");
        String name = config.getString("database.name");
        String user = config.getString("database.user");
        String password = config.getString("database.password");
        this.db = new ConnectionHolder(host, name, user, password);
        if (Bukkit.getOnlinePlayers().size() > 0) {
            logger.info("Connecting to the database in background thread...");
            new ActionableResult.ConditionalInvocableResult<>(new Thread(this::connect))
                    .always(Thread::start)
                    .always(thread -> thread.setUncaughtExceptionHandler((t, e) -> {
                        logger.error("Failed to connect to the database in background thread");
                        e.printStackTrace();
                        Bukkit.getScheduler().runTask(UniversalLanguageSelector.this, () ->
                                Bukkit.getPluginManager().disablePlugin(UniversalLanguageSelector.this));
                    }));
        } else {
            logger.info("Connecting to the database in main thread...");
            this.connect();
        }
    }

    private void connect() {
        try {
            this.db.connect();
            logger.info("Connected to the database");
        } catch (SQLException e) {
            throw new RuntimeException("Could not connect to the database", e);
        }
    }

    @Override
    public void setLanguage(@NotNull Player player, @Nullable Language language) {
        LanguageChangeEvent event = new LanguageChangeEvent(player, language, LanguageChangeEvent.Cause.PLUGIN);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return;
        throw new UnsupportedOperationException("todo");
    }

    @Override
    @NotNull
    public Language getLanguage(@NotNull Player player) {
        throw new UnsupportedOperationException("todo");
    }

    @NotNull
    @Override
    public LanguageDataAPI getLanguageDataAPI() { return this.db.language; }
}
