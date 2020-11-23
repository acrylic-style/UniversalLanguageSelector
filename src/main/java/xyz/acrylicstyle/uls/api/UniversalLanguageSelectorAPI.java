package xyz.acrylicstyle.uls.api;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import util.promise.Promise;
import xyz.acrylicstyle.uls.api.sql.LanguageDataAPI;

import java.util.UUID;

public interface UniversalLanguageSelectorAPI {
    void invalidateCache(@NotNull UUID uuid);

    /**
     * Requests BungeeCord or Spigot server to invalidate their cache. If called on Spigot server, it will invalidate
     * cache on the BungeeCord. If called on BungeeCord server, it will invalidate cache on Spigot server.
     * @param uuid the player
     */
    void requestInvalidateCache(@NotNull UUID uuid);

    @NotNull
    Promise<Void> setLanguage(@NotNull UUID player, @Nullable Language language);

    @NotNull
    Promise<Language> getLanguage(@NotNull UUID uuid);

    /**
     * Provides the raw access to the table.
     */
    @NotNull
    LanguageDataAPI getLanguageDataAPI();

    /**
     * Returns methods that is available only when running on spigot server.
     */
    @NotNull
    default BukkitAPI bukkit() { throw new UnsupportedOperationException("Not supported in this environment."); }

    /**
     * Returns methods that is available only when running on BungeeCord server.
     */
    @NotNull
    default BungeeCordAPI bungeeCord() { throw new UnsupportedOperationException("Not supported in this environment."); }

    interface BukkitAPI {
        @NotNull
        Promise<Void> setLanguage(@NotNull org.bukkit.entity.Player player, @Nullable Language language);

        @NotNull
        Promise<Language> getLanguage(@NotNull org.bukkit.entity.Player player);

        void openSelectLanguageGui(@NotNull org.bukkit.entity.Player player);
    }

    interface BungeeCordAPI {
    }
}
