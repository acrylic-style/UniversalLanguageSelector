package xyz.acrylicstyle.uls.api.sql;

import org.jetbrains.annotations.NotNull;
import util.promise.Promise;
import xyz.acrylicstyle.uls.api.Language;

import java.util.UUID;

/**
 * Provides the raw access to the table.
 */
public interface LanguageDataAPI {
    @NotNull
    Promise<Void> set(@NotNull UUID uuid, @NotNull Language language);

    @NotNull
    Promise<Language> get(@NotNull UUID uuid);

    @NotNull
    Promise<Boolean> has(@NotNull UUID uuid);
}
