package lebib.team.config;


import lebib.team.entity.CategoryEntity;
import lebib.team.entity.ProductEntity;
import lebib.team.entity.ProductImageEntity;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HibernateConfig {

    private String driver;

    private String url;

    private String username;

    private String password;

    private String dialect;

    private String showSql;

    private String hibernateMode;

    @Bean
    public SessionFactory sessionFactory() {
        org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration();

        configuration
                .addAnnotatedClass(CategoryEntity.class)
                .addAnnotatedClass(ProductEntity.class)
                .addAnnotatedClass(ProductImageEntity.class)
                .setProperty("hibernate.connection.driver_class", driver)
                .setProperty("hibernate.connection.url", url)
                .setProperty("hibernate.connection.username", username)
                .setProperty("hibernate.connection.password", password)
                .setProperty("hibernate.dialect", dialect)
                .setProperty("hibernate.show_sql", showSql)
                .setProperty("hibernate.hbm2ddl.auto", hibernateMode);

        return configuration.buildSessionFactory();
    }
}

