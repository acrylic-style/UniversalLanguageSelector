package xyz.acrylicstyle.uls.bungee;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import xyz.acrylicstyle.uls.api.ChannelConstants;
import xyz.acrylicstyle.uls.api.UniversalLanguageSelectorAPIProvider;
import xyz.acrylicstyle.uls.api.sql.ConnectionHolder;
import xyz.acrylicstyle.uls.bungee.channel.BungeeChannelListener;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class UniversalLanguageSelectorPlugin extends UniversalLanguageSelectorImpl {
    private final Configuration config;

    {
        Configuration c;
        try {
            c = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File("./plugins/UniversalLanguageSelector/config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
            c = null;
        }
        config = c;
    }

    @Override
    public void onLoad() {
        UniversalLanguageSelectorAPIProvider.setAPI(this);
    }

    @Override
    public void onEnable() {
        getProxy().registerChannel(ChannelConstants.INVALIDATE_CACHE);
        getProxy().getPluginManager().registerListener(this, new BungeeChannelListener());
        String host = config.getString("database.host");
        String name = config.getString("database.name");
        String user = config.getString("database.user");
        String password = config.getString("database.password");
        this.db = new ConnectionHolder(host, name, user, password);
        getLogger().info("Connecting to the database...");
        connect();
    }

    private void connect() {
        try {
            this.db.connect();
            getLogger().info("Connected to the database");
        } catch (SQLException e) {
            throw new RuntimeException("Could not connect to the database", e);
        }
    }
}
