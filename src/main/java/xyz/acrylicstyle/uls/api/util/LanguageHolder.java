package xyz.acrylicstyle.uls.api.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import util.ActionableResult;
import util.Collection;
import util.promise.IPromise;
import util.promise.Promise;
import xyz.acrylicstyle.tomeito_api.utils.NamespacedKey;
import xyz.acrylicstyle.uls.api.Language;
import xyz.acrylicstyle.uls.api.UniversalLanguageSelectorAPIProvider;

import java.util.UUID;

public class LanguageHolder {
    private LanguageHolder() {}

    @NotNull
    private static final LanguageHolder INSTANCE = new LanguageHolder();

    @NotNull
    public static LanguageHolder instance() { return INSTANCE; }

    private final Collection<NamespacedKey, LanguageMap> languageMap = new Collection<>();

    @NotNull
    public ActionableResult<LanguageMap> get(@NotNull NamespacedKey namespacedKey) {
        return ActionableResult.ofNullable(languageMap.find(namespacedKey));
    }

    @Contract(pure = true)
    @NotNull
    public Promise<String> get(@NotNull NamespacedKey namespacedKey, @NotNull UUID player) {
        return UniversalLanguageSelectorAPIProvider.getAPI().getLanguage(player).then((IPromise<Language, String>) language -> get(namespacedKey, language));
    }

    @NotNull
    public String get(@NotNull NamespacedKey namespacedKey, @NotNull Language language) {
        return get(namespacedKey)
                .orElseThrow(() -> new IllegalArgumentException("LanguageMap under " + namespacedKey + " is not registered!"))
                .get(language);
    }

    public void register(@NotNull NamespacedKey namespacedKey, @NotNull Language language, @NotNull String message) {
        get(namespacedKey).orElseGet(() -> this.registerLanguageMap(namespacedKey)).register(language, message);
    }

    public void unregister(@NotNull NamespacedKey namespacedKey, @NotNull Language language) {
        get(namespacedKey).ifPresent(map -> map.unregister(language));
    }

    public void unregister(@NotNull NamespacedKey namespacedKey) {
        languageMap.remove(namespacedKey);
    }

    @NotNull
    private LanguageMap registerLanguageMap(@NotNull NamespacedKey namespacedKey) {
        LanguageMap map = new LanguageMap();
        languageMap.add(namespacedKey, map);
        return map;
    }
}
