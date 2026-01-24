package ru.makan1.bankapp.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.makan1.bankapp.model.Account;
import ru.makan1.bankapp.model.User;

@Configuration
@PropertySource("classpath:application.properties")
public class HibernateConfiguration {

    @Value("${hibernate.connection.url}")
    String connectionUrl;

    @Value("${hibernate.connection.username}")
    String username;

    @Value("${hibernate.connection.password}")
    String password;

    @Value("${hibernate.connection.driver_class}")
    String driver;

    @Bean
    public SessionFactory sessionFactory() {
        org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration();

        configuration.addPackage("ru.makan1.bankapp")
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Account.class)
                .setProperty("hibernate.connection.driver_class", driver)
                .setProperty("hibernate.connection.url", connectionUrl)
                .setProperty("hibernate.connection.username", username)
                .setProperty("hibernate.connection.password", password)
                .setProperty("hibernate.show_sql", "true")
                .setProperty("hibernate.hbm2ddl.auto", "update");

        return configuration.buildSessionFactory();
    }
}
