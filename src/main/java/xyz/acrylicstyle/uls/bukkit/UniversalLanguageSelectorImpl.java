package xyz.acrylicstyle.uls.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import util.ActionableResult;
import util.promise.Promise;
import util.ref.DataCache;
import xyz.acrylicstyle.tomeito_api.TomeitoAPI;
import xyz.acrylicstyle.uls.api.ChannelConstants;
import xyz.acrylicstyle.uls.api.Language;
import xyz.acrylicstyle.uls.api.UniversalLanguageSelectorAPI;
import xyz.acrylicstyle.uls.api.sql.LanguageDataAPI;
import xyz.acrylicstyle.uls.api.util.LanguageHolder;
import xyz.acrylicstyle.uls.api.event.LanguageChangeEvent;
import xyz.acrylicstyle.uls.bukkit.channel.BukkitChannelListener;
import xyz.acrylicstyle.uls.bukkit.gui.SelectLanguageGui;
import xyz.acrylicstyle.uls.api.sql.ConnectionHolder;
import xyz.acrylicstyle.uls.api.util.LanguageKeys;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class UniversalLanguageSelectorImpl extends JavaPlugin implements UniversalLanguageSelectorAPI {
    private final Map<UUID, DataCache<Language>> cache = new HashMap<>();
    protected final SelectLanguageGui selectLanguageGui = new SelectLanguageGui(this);
    private final BukkitAPIImpl bukkitAPI = new BukkitAPIImpl();

    public ConnectionHolder db;

    @Override
    public void invalidateCache(@NotNull UUID uuid) { cache.remove(uuid); }

    @Override
    public void requestInvalidateCache(@NotNull UUID uuid) {
        invalidateCache(uuid);
        ActionableResult.ofNullable(TomeitoAPI.getOnlinePlayers().first()).ifPresent(p ->
                BukkitChannelListener.sendToBungeeCord(p, ChannelConstants.INVALIDATE_CACHE, uuid.toString(), null)
        );
    }

    @Override
    public @NotNull Promise<Void> setLanguage(@NotNull UUID player, @Nullable Language language) {
        if (language == null) language = Language.DEFAULT;
        LanguageChangeEvent event = new LanguageChangeEvent(null, player, language, LanguageChangeEvent.Cause.PLUGIN);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return Promise.getEmptyPromise();
        return this.db.language.set(player, language);
    }

    @NotNull
    @Override
    public Promise<Language> getLanguage(@NotNull UUID uuid) {
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

    @NotNull
    @Override
    public LanguageDataAPI getLanguageDataAPI() { return this.db.language; }

    @Override
    public @NotNull BukkitAPI bukkit() { return bukkitAPI; }

    @Override
    public @NotNull String getImplName() {
        return "UniversalLanguageSelector (Bukkit)";
    }

    @Override
    public @Nullable String getVersion() {
        return getDescription().getVersion();
    }

    public class BukkitAPIImpl extends UniversalLanguageSelectorAPI.BukkitAPI {
        @NotNull
        @Override
        public Promise<Void> setLanguage(@NotNull Player player, @Nullable Language language) {
            if (language == null) language = Language.DEFAULT;
            LanguageChangeEvent event = new LanguageChangeEvent(player, player.getUniqueId(), language, LanguageChangeEvent.Cause.PLUGIN);
            Bukkit.getPluginManager().callEvent(event);
            if (event.isCancelled()) return Promise.getEmptyPromise();
            return UniversalLanguageSelectorImpl.this.db.language.set(player.getUniqueId(), language);
        }

        @Override
        @NotNull
        public Promise<Language> getLanguage(@NotNull Player player) {
            return UniversalLanguageSelectorImpl.this.getLanguage(player.getUniqueId());
        }

        @Override
        public void openSelectLanguageGui(@NotNull Player player) {
            LanguageHolder.instance().get(LanguageKeys.GUI_SELECT_LANGUAGE, player.getUniqueId()).then(selectLanguageMessage -> {
                TomeitoAPI.run(() -> player.openInventory(selectLanguageGui.getInventory(selectLanguageMessage)));
                return null;
            }).queue();
        }
    }
}
