package xyz.acrylicstyle.uls.api;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import util.promise.Promise;
import xyz.acrylicstyle.uls.api.sql.LanguageDataAPI;

import java.util.UUID;

public interface UniversalLanguageSelectorAPI {
    BukkitAPI BUKKIT_API = new BukkitAPI();
    BungeeCordAPI BUNGEE_CORD_API = new BungeeCordAPI();

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
    default BukkitAPI bukkit() { return BUKKIT_API; }

    /**
     * Returns methods that is available only when running on BungeeCord server.
     */
    @NotNull
    default BungeeCordAPI bungeeCord() { return BUNGEE_CORD_API; }

    /**
     * Returns the name of the implementation.
     * @return the name
     */
    @Nullable
    String getImplName();

    @Nullable
    String getVersion();

    class BukkitAPI {
        @NotNull
        public Promise<Void> setLanguage(@NotNull org.bukkit.entity.Player player, @Nullable Language language) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @NotNull
        public Promise<Language> getLanguage(@NotNull org.bukkit.entity.Player player) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void openSelectLanguageGui(@NotNull org.bukkit.entity.Player player) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    class BungeeCordAPI {
    }
}
