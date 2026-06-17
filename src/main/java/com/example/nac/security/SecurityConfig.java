package com.example.nac.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfig {

    @Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/auth/**").permitAll()
            .requestMatchers(
                "/",
                "/login.html",
                "/registration.html",
                "/forgotpassword.html",
                "/resetpassword.html",
                "/viewer.html",
                "/admin.html",
                "/dataentry.html"
            ).permitAll()
            .requestMatchers(
                "/css/**",
                "/js/**",
                "/images/**"
            ).permitAll()
            .anyRequest().permitAll() // 🔥 allow everything for now
        )
        .formLogin(form -> form.disable()); // ❗ DISABLE THIS

    return http.build();
}
     

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}