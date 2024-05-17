package com.springboot.olympics2024;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import service.*;
import validator.OlympicNumberValidator;

import java.util.Locale;

@SpringBootApplication
@EnableJpaRepositories("repository")
@EntityScan("domain")
public class Olympics2024Application implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(Olympics2024Application.class, args);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "/olympics2024");
        registry.addViewController("/accessdenied").setViewName("accessdenied");
    }

    @Bean
    SpectatorDetailsService spectatorDetailsService() {
        return new SpectatorDetailsService();
    }

    @Bean
    SportService sportService() {
        return new SportServiceImpl();
    }

    @Bean
    GameService gameService() {
        return new GameServiceImpl();
    }

    @Bean
    StadiumService stadiumService() {
        return new StadiumServiceImpl();
    }

    @Bean
    OlympicNumberValidator olympicNumberValidator() {
        return new OlympicNumberValidator();
    }

    @Bean
    LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.ENGLISH);
        return slr;
    }

}
