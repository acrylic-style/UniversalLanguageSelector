package xyz.acrylicstyle.uls.api;

import org.jetbrains.annotations.NotNull;
import util.base.Enums;

public enum Language {
    ENGLISH,
    JAPANESE,
    ;

    @NotNull
    public static Language get(@NotNull String key) { return get(key, JAPANESE); }

    @NotNull
    public static Language get(@NotNull String key, @NotNull Language def) {
        return Enums.valueOf(Language.class, key.toUpperCase()).orElse(def);
    }
}
