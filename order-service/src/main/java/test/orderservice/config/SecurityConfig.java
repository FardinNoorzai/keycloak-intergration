package test.billingservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.*;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

  @Bean
  SecurityFilterChain filter(HttpSecurity http) throws Exception {
    http
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/actuator/health").permitAll()
                    .requestMatchers("/api/internal/**").hasRole("ORDER_READ")
                    .anyRequest().authenticated()
            )
            .oauth2ResourceServer(oauth -> oauth.jwt(jwt -> jwt.jwtAuthenticationConverter(this::toAuth)));
    return http.build();
  }

  private AbstractAuthenticationToken toAuth(Jwt jwt) {
    Set<GrantedAuthority> authorities = new HashSet<>(new JwtGrantedAuthoritiesConverter().convert(jwt));

    Map<String, Object> realmAccess = jwt.getClaim("realm_access");
    if (realmAccess != null) {
      Object rolesObj = realmAccess.get("roles");
      if (rolesObj instanceof Collection<?> roles) {
        roles.stream()
                .filter(Objects::nonNull)
                .map(Object::toString)
                .map(r -> new SimpleGrantedAuthority("ROLE_" + r))
                .forEach(authorities::add);
      }
    }

    Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
    if (resourceAccess != null) {
      Object clientAccess = resourceAccess.get("order-service");
      if (clientAccess instanceof Map<?, ?> clientMap) {
        Object clientRolesObj = clientMap.get("roles");
        if (clientRolesObj instanceof Collection<?> clientRoles) {
          clientRoles.stream()
                  .filter(Objects::nonNull)
                  .map(Object::toString)
                  .map(r -> new SimpleGrantedAuthority("ROLE_" + r))
                  .forEach(authorities::add);
        }
      }
    }

    return new JwtAuthenticationToken(jwt, authorities, jwt.getSubject());
  }
}
