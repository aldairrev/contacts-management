package pe.aldairrev.cm.configs;

import java.sql.SQLException;

import com.zaxxer.hikari.HikariDataSource;

import org.flywaydb.core.internal.jdbc.JdbcTemplate;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DataSourceConfig {
    @Bean
    @Primary
    @ConfigurationProperties("app.datasource.main")
    public HikariDataSource hikariDataSource() {
        return DataSourceBuilder
            .create()
            .type(HikariDataSource.class)
            .build();
    }

    @Bean("jdbcConnection")
    public JdbcTemplate jdbcTemplate(HikariDataSource hikariDataSource) throws SQLException {
        return new JdbcTemplate(hikariDataSource.getConnection());
    }
}
