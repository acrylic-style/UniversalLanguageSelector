package xyz.acrylicstyle.uls.api;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import util.Validate;

public final class UniversalLanguageSelectorAPIProvider {
    private static UniversalLanguageSelectorAPI api = null;

    @Contract(pure = true)
    @NotNull
    public static UniversalLanguageSelectorAPI getAPI() { return Validate.notNull(api, "API isn't defined yet or you are running on unsupported environment"); }

    public static void setAPI(@NotNull UniversalLanguageSelectorAPI api) {
        Validate.notNull(api, "API cannot be null");
        if (UniversalLanguageSelectorAPIProvider.api != null) throw new IllegalArgumentException("Cannot redefine API singleton");
        UniversalLanguageSelectorAPIProvider.api = api;
        System.out.println("[UniversalLanguageSelector] Running " + getAPI().getImplName() + " version " + getAPI().getVersion());
    }
}
