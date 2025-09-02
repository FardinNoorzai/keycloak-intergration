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
            // Disable default login page and form login
            .oauth2Login().disable()
            .formLogin().disable()
            
            .authorizeHttpRequests(auth -> auth
                // Health checks open
                .requestMatchers("/actuator/health").permitAll()
                // Internal endpoints for service-to-service calls
                .requestMatchers("/api/internal/**").hasRole("ORDER_READ")
                // Billing endpoints (optional)
                .requestMatchers("/billing/**").authenticated()
                // everything else must be authenticated
                .anyRequest().authenticated()
            )
            
            // No session creation for service-to-service
            .sessionManagement(session -> session.disable())
            
            // Billing service does not act as a resource server (unless exposing APIs)
            .oauth2ResourceServer(oauth2 -> oauth2.jwt().disable());
        
        return http.build();
    }
}
