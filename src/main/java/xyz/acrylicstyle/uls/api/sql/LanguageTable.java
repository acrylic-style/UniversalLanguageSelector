package xyz.acrylicstyle.uls.api.sql;

import org.jetbrains.annotations.NotNull;
import util.promise.Promise;
import xyz.acrylicstyle.sql.Table;
import xyz.acrylicstyle.sql.options.FindOptions;
import xyz.acrylicstyle.sql.options.UpsertOptions;
import xyz.acrylicstyle.uls.api.Language;
import xyz.acrylicstyle.uls.api.UniversalLanguageSelectorAPIProvider;

import java.util.Objects;
import java.util.UUID;

public class LanguageTable implements LanguageDataAPI {
    public final Table table;

    LanguageTable(@NotNull Table table) { this.table = table; }

    @NotNull
    @Override
    public Promise<Void> set(@NotNull UUID uuid, @NotNull Language language) {
        return this.table.upsert(
                new UpsertOptions.Builder()
                        .addWhere("uuid", uuid.toString())
                        .addValue("uuid", uuid.toString())
                        .addValue("language", language.name())
                        .build()
        ).then(data -> {
            UniversalLanguageSelectorAPIProvider.getAPI().requestInvalidateCache(uuid);
            return null;
        });
    }

    @NotNull
    @Override
    public Promise<Language> get(@NotNull UUID uuid) {
        return this.table.findOne(new FindOptions.Builder().addWhere("uuid", uuid.toString()).build())
                .then(td -> td == null ? Language.DEFAULT : Language.get(td.getString("language")));
    }

    @NotNull
    public Promise<Boolean> has(@NotNull UUID uuid) {
        return this.table.findOne(new FindOptions.Builder().addWhere("uuid", uuid.toString()).build()).then(Objects::nonNull);
    }
}
