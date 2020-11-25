package xyz.acrylicstyle.uls.api.util;

import xyz.acrylicstyle.tomeito_api.utils.NamespacedKey;
import xyz.acrylicstyle.uls.api.Language;

public class LanguageKeys {
    private static final String namespace = "uls";

    public static final NamespacedKey LANGUAGE = new NamespacedKey(namespace, "language");
    public static final NamespacedKey GUI_SELECT_LANGUAGE = new NamespacedKey(namespace, "gui/select_language");
    public static final NamespacedKey GUI_CHANGED_LANGUAGE = new NamespacedKey(namespace, "gui/changed_language");
    public static final NamespacedKey SET_LANGUAGE_AUTOMATICALLY = new NamespacedKey(namespace, "set_language_automatically");

    static {
        LanguageHolder holder = LanguageHolder.instance();
        holder.register(LANGUAGE, Language.ENGLISH, "English");
        holder.register(LANGUAGE, Language.JAPANESE, "日本語");
        holder.register(GUI_SELECT_LANGUAGE, Language.ENGLISH, "Select language");
        holder.register(GUI_SELECT_LANGUAGE, Language.JAPANESE, "言語を選択");
        holder.register(GUI_CHANGED_LANGUAGE, Language.ENGLISH, "Changed your language to %s. May takes up to 10 minutes to apply changes.");
        holder.register(GUI_CHANGED_LANGUAGE, Language.JAPANESE, "言語を %s に変更しました。変更が反映されるまで最大10分かかります。");
        holder.register(SET_LANGUAGE_AUTOMATICALLY, Language.ENGLISH, "Your language was set to %s automatically. Please use /lang if you want to set language manually.");
        holder.register(SET_LANGUAGE_AUTOMATICALLY, Language.JAPANESE, "言語が %s に設定されました。手動で設定するには /lang を使ってください。");
    }
}
