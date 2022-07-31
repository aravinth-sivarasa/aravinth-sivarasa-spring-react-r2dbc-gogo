package io.product.rnd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.DatabasePopulator;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

import io.r2dbc.spi.ConnectionFactory;

@SpringBootApplication
public class RNDMain {
    public static void main(String[] args) {
        SpringApplication.run(RNDMain.class, args);
    }

    @Bean
    ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        initializer.setDatabasePopulator(datebasePopulator());
        return initializer;
    }

    private DatabasePopulator datebasePopulator() {
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        // resourceDatabasePopulator.addScript(new ClassPathResource("schema.sql"));
        // resourceDatabasePopulator.addScript(new ClassPathResource("BNUserDB.sql"));
        return resourceDatabasePopulator;
    }
}