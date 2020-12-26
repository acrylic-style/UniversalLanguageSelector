package xyz.acrylicstyle.uls.api;

import org.jetbrains.annotations.NotNull;
import util.CollectionList;
import util.ICollectionList;
import util.base.Enums;

public enum Language {
    ENGLISH,
    JAPANESE,
    CHINESE,
    ;

    @NotNull
    public static final Language DEFAULT = JAPANESE;

    @NotNull
    public static Language get(@NotNull String key) { return get(key, DEFAULT); }

    @NotNull
    public static Language get(@NotNull String key, @NotNull Language def) {
        return Enums.valueOf(Language.class, key.toUpperCase()).orElse(def);
    }

    @NotNull
    public static CollectionList<Language> valuesList() { return (CollectionList<Language>) ICollectionList.asList(values()); }
}
