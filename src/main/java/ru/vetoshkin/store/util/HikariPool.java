package ru.vetoshkin.store.util;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.postgresql.ds.PGPoolingDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
public class HikariPool {

    private static final String propertiesLocation = "db.properties";
    private static DataSource source;


    public static void init() throws IOException {
        Properties dbProperties = PropertiesUtil.loadProperties(propertiesLocation);

        HikariConfig configuration = new HikariConfig();
        PGPoolingDataSource pg_source = new PGPoolingDataSource();
        pg_source.setDatabaseName(dbProperties.getProperty("db.initialDb"));
        pg_source.setServerName(dbProperties.getProperty("db.host"));
        pg_source.setPortNumber(Integer.parseInt(dbProperties.getProperty("db.port")));
        pg_source.setUser(dbProperties.getProperty("db.user"));
        pg_source.setPassword(dbProperties.getProperty("db.password"));

        configuration.setDataSource(pg_source);
        source = new HikariDataSource(configuration);
    }


    public static DataSource getSource() {
        return source;
    }

}
