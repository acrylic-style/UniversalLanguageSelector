package xyz.acrylicstyle.uls.api.sql;

import org.jetbrains.annotations.NotNull;
import xyz.acrylicstyle.sql.DataType;
import xyz.acrylicstyle.sql.Sequelize;
import xyz.acrylicstyle.sql.TableDefinition;
import xyz.acrylicstyle.uls.api.Language;

import java.sql.SQLException;
import java.util.Properties;

public class ConnectionHolder extends Sequelize {
    public LanguageTable language;

    public ConnectionHolder(@NotNull String host, @NotNull String database, @NotNull String user, @NotNull String password) {
        super(host, database, user, password);
    }

    public void connect() throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("maxReconnects", "3");
        properties.setProperty("autoReconnect", "true");
        authenticate(getMySQLDriver(), properties);
        language = new LanguageTable(define("language", new TableDefinition[]{
                new TableDefinition.Builder("uuid", DataType.STRING).setAllowNull(false).setPrimaryKey(true).build(),
                new TableDefinition.Builder("language", DataType.STRING).setAllowNull(false).setDefaultValue(Language.DEFAULT.name()).build(),
        }));
        sync();
    }
}
