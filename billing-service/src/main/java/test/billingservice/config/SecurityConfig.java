package test.billingservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/billing/orders").permitAll() // anyone can call for testing
                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.disable())   // disable CSRF for APIs
                .formLogin().disable();         // disable login page
        return http.build();
    }
}
