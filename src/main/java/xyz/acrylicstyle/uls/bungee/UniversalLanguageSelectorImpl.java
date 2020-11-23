package xyz.acrylicstyle.uls.bungee;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import util.promise.Promise;
import util.ref.DataCache;
import xyz.acrylicstyle.uls.api.ChannelConstants;
import xyz.acrylicstyle.uls.api.Language;
import xyz.acrylicstyle.uls.api.UniversalLanguageSelectorAPI;
import xyz.acrylicstyle.uls.api.sql.ConnectionHolder;
import xyz.acrylicstyle.uls.api.sql.LanguageDataAPI;
import xyz.acrylicstyle.uls.bungee.channel.BungeeChannelListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class UniversalLanguageSelectorImpl extends Plugin implements UniversalLanguageSelectorAPI, UniversalLanguageSelectorAPI.BungeeCordAPI {
    private final Map<UUID, DataCache<Language>> cache = new HashMap<>();

    public ConnectionHolder db;

    public void invalidateCache(@NotNull UUID uuid) {
        cache.remove(uuid);
    }

    @Override
    public void requestInvalidateCache(@NotNull UUID uuid) {
        invalidateCache(uuid);
        ProxiedPlayer player = ProxyServer.getInstance().getPlayer(uuid);
        if (player == null) return;
        BungeeChannelListener.sendToBukkit(ChannelConstants.INVALIDATE_CACHE, uuid.toString(), null, player.getServer().getInfo());
    }

    @Override
    public @NotNull Promise<Void> setLanguage(@NotNull UUID player, @Nullable Language language) {
        if (language == null) language = Language.DEFAULT;
        return this.db.language.set(player, language);
    }

    @Override
    public @NotNull Promise<Language> getLanguage(@NotNull UUID uuid) {
        if (cache.containsKey(uuid)) {
            Optional<Language> cached = cache.get(uuid).getOptional();
            if (cached.isPresent()) return Promise.of(cached.get()); // valid cache
            // cache expired
        }
        return this.db.language.get(uuid).then(language -> {
            cache.put(uuid, new DataCache<>(language, System.currentTimeMillis() + 1000 * 60 * 10)); // cache for up to 10 minutes
            return language;
        });
    }

    @Override
    public @NotNull LanguageDataAPI getLanguageDataAPI() {
        return this.db.language;
    }

    @Override
    public @NotNull BungeeCordAPI bungeeCord() { return this; }
}
