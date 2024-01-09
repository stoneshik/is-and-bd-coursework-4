package com.main.security;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


@Configuration
@ComponentScan
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthorizeFilter authorizeFilter;


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(
                        (httpSecuritySessionManagementConfigurer) ->
                                httpSecuritySessionManagementConfigurer.sessionCreationPolicy(
                                        SessionCreationPolicy.ALWAYS
                                )
                )
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(
                                "/css/**",
                                "/img/**",
                                "/js/**",
                                "/static/**",
                                "/manifest.json",
                                "/favicon.ico",
                                "/index.html",
                                "/",
                                "/login",
                                "/register",
                                "/map_without_login",
                                "/aboba"
                        ).permitAll()
                        //.anyRequest().authenticated()
                )
                .addFilterAfter(authorizeFilter, BasicAuthenticationFilter.class)
                .logout(logout -> logout.deleteCookies("JSESSIONID"));
        return http.build();
    }
}
