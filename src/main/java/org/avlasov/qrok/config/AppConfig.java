package org.avlasov.qrok.config;

import org.avlasov.qrok.config.datasource.DevDataSourceConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by artemvlasov on 10/07/2017.
 */
@Configuration

@ComponentScan(basePackages = "org.avlasov.qrok")
@EnableJpaRepositories(basePackages = "org.avlasov.qrok.repository")
@Import(DevDataSourceConfig.class)
public class AppConfig {

}
