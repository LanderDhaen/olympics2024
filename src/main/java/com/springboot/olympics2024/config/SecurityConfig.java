package com.springboot.olympics2024.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import service.SpectatorDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

    @Autowired
    private SpectatorDetailsService spectatorDetailsService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(spectatorDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }


    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.csrfTokenRepository(new HttpSessionCsrfTokenRepository()));

        http.authorizeHttpRequests(requests ->
                        requests.requestMatchers("/login**").permitAll()
                                .requestMatchers("/css/**").permitAll()
                                .requestMatchers("/accessdenied**").permitAll()
                                .requestMatchers("/olympics2024").hasAnyRole("USER", "ADMIN")
                                .requestMatchers("/olympics2024/sports/*").hasAnyRole("USER", "ADMIN")
                                .requestMatchers("/olympics2024/sports/*/games/create").hasRole("ADMIN")
                                .requestMatchers("/olympics2024/sports/*/games/*/buy").hasRole("USER")
                                .requestMatchers("/olympics2024/tickets").hasRole("USER")
                                .requestMatchers("/rest/sports/*/games").hasRole("ADMIN")
                                .requestMatchers("/rest/games/*/seats").hasRole("ADMIN"))

                                .formLogin(form ->
                        form
                                .defaultSuccessUrl("/olympics2024", true)
                                .loginPage("/login"))
                .exceptionHandling(configurer -> configurer
                .accessDeniedPage("/accessdenied"));

        return http.build();

    }
}
