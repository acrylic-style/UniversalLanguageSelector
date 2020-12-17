package xyz.acrylicstyle.uls.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import util.ActionableResult;
import util.reflector.Reflector;
import xyz.acrylicstyle.tomeito_api.TomeitoAPI;
import xyz.acrylicstyle.tomeito_api.command.PlayerCommandExecutor;
import xyz.acrylicstyle.tomeito_api.providers.ConfigProvider;
import xyz.acrylicstyle.tomeito_api.utils.Log;
import xyz.acrylicstyle.uls.api.ChannelConstants;
import xyz.acrylicstyle.uls.api.Language;
import xyz.acrylicstyle.uls.api.UniversalLanguageSelectorAPIProvider;
import xyz.acrylicstyle.uls.bukkit.channel.BukkitChannelListener;
import xyz.acrylicstyle.uls.bukkit.listener.*;
import xyz.acrylicstyle.uls.api.sql.ConnectionHolder;

import java.sql.SQLException;

public class UniversalLanguageSelectorPlugin extends UniversalLanguageSelectorImpl {
    public final ConfigProvider config = new ConfigProvider("./plugins/UniversalLanguageSelector/config.yml");
    public final Log.Logger logger = Log.of("UniversalLanguageSelector");

    public UniversalLanguageSelectorPlugin() { UniversalLanguageSelectorAPIProvider.setAPI(this); }

    @Override
    public void onEnable() {
        Reflector.classLoader = this.getClassLoader();
        getServer().getMessenger().registerIncomingPluginChannel(this, ChannelConstants.INVALIDATE_CACHE, new BukkitChannelListener());
        getServer().getMessenger().registerOutgoingPluginChannel(this, ChannelConstants.INVALIDATE_CACHE);
        TomeitoAPI.registerCommand("language", new PlayerCommandExecutor() {
            @Override
            public void onCommand(@NotNull Player player, @NotNull String[] args) {
                if (args.length > 0) {
                    Language language = Language.valuesList().filter(l -> l.name().equalsIgnoreCase(args[0])).first();
                    if (language == null) {
                        player.sendMessage(ChatColor.RED + "Supported languages: " + ChatColor.YELLOW + Language.valuesList().map(Language::name).join(ChatColor.RED + ", " + ChatColor.YELLOW));
                        return;
                    }
                    return;
                }
                bukkit().openSelectLanguageGui(player);
            }
        });
        Bukkit.getPluginManager().registerEvents(new EventListener(this), this);
        String host = config.getString("database.host");
        String name = config.getString("database.name");
        String user = config.getString("database.user");
        String password = config.getString("database.password");
        this.db = new ConnectionHolder(host, name, user, password);
        if (Bukkit.getOnlinePlayers().size() > 0) {
            logger.info("Connecting to the database in background thread...");
            ActionableResult.of(new Thread(this::connect))
                    .then(Thread::start)
                    .then(thread -> thread.setUncaughtExceptionHandler((t, e) -> {
                        logger.error("Failed to connect to the database in background thread");
                        e.printStackTrace();
                        Bukkit.getScheduler().runTask(UniversalLanguageSelectorPlugin.this, () ->
                                Bukkit.getPluginManager().disablePlugin(UniversalLanguageSelectorPlugin.this));
                    }));
        } else {
            logger.info("Connecting to the database in main thread...");
            this.connect();
        }
        selectLanguageGui.register();
    }

    private void connect() {
        try {
            this.db.connect();
            logger.info("Connected to the database");
        } catch (SQLException e) {
            throw new RuntimeException("Could not connect to the database", e);
        }
    }
}
