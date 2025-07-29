package com.example.demo.configuration;

import com.example.demo.configuration.property.PostgresProperty;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class PostgresConfiguration {

    @Primary
    @Bean("postgresDataSource")
    public DataSource postgresDataSource(PostgresProperty property, MeterRegistry meterRegistry) {

        HikariConfig hikariConfig = new HikariConfig();

        // Connection properties
        hikariConfig.setDriverClassName(property.driverClassname());
        hikariConfig.setJdbcUrl(property.url());
        hikariConfig.setUsername(property.username());
        hikariConfig.setPassword(property.password());

        // Pool sizing
        hikariConfig.setMinimumIdle(property.connection().minimum().idle());
        hikariConfig.setMaximumPoolSize(property.connection().maximum().poolSize());

        // Timeout configurations
        hikariConfig.setConnectionTimeout(property.connection().connectionTimeout().toMillis());
        hikariConfig.setValidationTimeout(property.connection().validationTimeout().toMillis());
        hikariConfig.setMaxLifetime(property.connection().maxLifetime().toMillis());
        hikariConfig.setIdleTimeout(property.connection().idleTimeout().toMillis());

        // SQL configurations
        hikariConfig.setConnectionInitSql(property.connection().connectionInitSql());
        hikariConfig.setConnectionTestQuery(property.connection().connectionTestQuery());

        // Other configurations
        hikariConfig.setAllowPoolSuspension(property.connection().allowPoolSuspension());
        hikariConfig.setRegisterMbeans(property.connection().registerMbeans());
        hikariConfig.setMetricRegistry(meterRegistry);
        hikariConfig.setPoolName(property.connection().connectionPoolName());

        // Additional Postgres specific properties
        hikariConfig.addDataSourceProperty("defaultRowFetchSize", property.defaultRowFetchSize());
        return new HikariDataSource(hikariConfig);
    }
}
