package xyz.acrylicstyle.uls.plugin.sql;

import org.jetbrains.annotations.NotNull;
import xyz.acrylicstyle.sql.Table;
import xyz.acrylicstyle.uls.api.sql.LanguageDataAPI;

public class LanguageTable implements LanguageDataAPI {
    public final Table table;

    LanguageTable(@NotNull Table table) { this.table = table; }
}
