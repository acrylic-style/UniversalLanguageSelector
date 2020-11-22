package xyz.acrylicstyle.uls.api;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.acrylicstyle.uls.api.sql.LanguageDataAPI;

public interface UniversalLanguageSelectorAPI {
    void setLanguage(@NotNull Player player, @Nullable Language language);

    @NotNull
    Language getLanguage(@NotNull Player player);

    /**
     * Provides the raw access to the table. Methods on {@link LanguageDataAPI} is not cached and actions should
     * not be performed on the main thread.
     */
    @NotNull
    LanguageDataAPI getLanguageDataAPI();
}
