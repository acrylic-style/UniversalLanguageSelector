package xyz.acrylicstyle.uls.api;

import org.jetbrains.annotations.NotNull;
import util.Validate;

public final class UniversalLanguageSelectorAPIProvider {
    private static UniversalLanguageSelectorAPI api = null;

    @NotNull
    public static UniversalLanguageSelectorAPI getAPI() { return Validate.notNull(api, "API isn't defined yet!"); }

    public static void setAPI(@NotNull UniversalLanguageSelectorAPI api) {
        if (UniversalLanguageSelectorAPIProvider.api != null) throw new IllegalArgumentException("Cannot redefine API singleton");
        Validate.notNull(api, "API cannot be null");
        UniversalLanguageSelectorAPIProvider.api = api;
    }
}
