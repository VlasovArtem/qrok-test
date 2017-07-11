package org.avlasov.qrok.repository.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

/**
 * Created by artemvlasov on 11/07/2017.
 */
@TestConfiguration
@ComponentScan(basePackages = "org.avlasov.qrok.repository")
public class DatabaseConfig {

    @Bean(destroyMethod = "shutdown")
    public static EmbeddedDatabase dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("scripts/schema.sql")
                .build();
    }

}
