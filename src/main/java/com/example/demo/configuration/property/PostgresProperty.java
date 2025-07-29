package com.example.demo.configuration.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties("service.postgres")
public record PostgresProperty(String url, String username, String password, String driverClassname,
                               Connection connection, int defaultRowFetchSize) {

    public record Connection(Minimum minimum, Maximum maximum, Duration connectionTimeout, Duration validationTimeout,
                             Duration maxLifetime, Duration idleTimeout, String connectionInitSql,
                             String connectionTestQuery, boolean allowPoolSuspension, boolean registerMbeans,
                             String connectionPoolName) {

        public record Minimum(int idle) {
        }

        public record Maximum(int poolSize) {
        }
    }
}