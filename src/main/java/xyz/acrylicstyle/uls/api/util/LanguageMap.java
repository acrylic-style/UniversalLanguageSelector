package xyz.acrylicstyle.uls.api.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import util.ActionableResult;
import util.Collection;
import xyz.acrylicstyle.uls.api.Language;

public class LanguageMap {
    private final Collection<Language, String> map = new Collection<>();
    private String defaultValue = null;

    public void register(@NotNull Language language, @NotNull String message) {
        if (language == Language.ENGLISH || defaultValue == null) defaultValue = message;
        map.add(language, message);
    }

    @Nullable
    public String unregister(@NotNull Language language) {
        return map.remove(language);
    }

    @NotNull
    public String get(@NotNull Language language) { return ActionableResult.ofNullable(map.getOrDefault(language, defaultValue)).orElse(""); }
}
