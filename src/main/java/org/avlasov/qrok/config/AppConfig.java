package org.avlasov.qrok.config;

import org.avlasov.qrok.config.datasource.DevDataSourceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by artemvlasov on 10/07/2017.
 */
@Configuration

@ComponentScan(basePackages = "org.avlasov.qrok")
@EnableJpaRepositories(basePackages = "org.avlasov.qrok.repository")
@EnableTransactionManagement
@Import(DevDataSourceConfig.class)
public class AppConfig {

    @Autowired
    private Environment environment;
    @Autowired
    private DataSource dataSource;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
        bean.setPackagesToScan("org.avlasov.qrok");
        Properties properties = new Properties();
        if(environment.acceptsProfiles("dev")) {
            properties.put("hibernate.show_sql", environment.getProperty("spring.jpa.show-sql"));
        }
        bean.setJpaProperties(properties);
        bean.setDataSource(dataSource);
        bean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        return bean;
    }

}
