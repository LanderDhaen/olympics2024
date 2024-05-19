package com.springboot.olympics2024.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }


    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.csrfTokenRepository(new HttpSessionCsrfTokenRepository()));

        http.authorizeHttpRequests(requests ->
                        requests.requestMatchers("/login**").permitAll()
                                .requestMatchers("/css/**").permitAll()
                                .requestMatchers("/accessdenied**").permitAll()
                                .requestMatchers("/olympics2024").hasAnyRole("USER", "ADMIN")
                                .requestMatchers("/olympics2024/sports/**").hasAnyRole("USER", "ADMIN")
                                .requestMatchers("/olympics2024/games/create/**").hasRole("ADMIN"))
                .formLogin(form ->
                        form
                                .defaultSuccessUrl("/olympics2024", true)
                                .loginPage("/login"))
                .exceptionHandling(configurer -> configurer
                .accessDeniedPage("/accessdenied"));

        return http.build();

    }
}
