package or.grisu.springwebsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> {

                    auth.requestMatchers("/v1/index2")
                            .permitAll();
                    auth.anyRequest().authenticated();


                })
                .formLogin(auth -> {
                    auth
                            .successHandler(successHandler())// Url redirección de sesión
                            .permitAll();
                })
                .sessionManagement(ses -> {
                    ses.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)// ALWAYS - IF_REQUIRED - NEVER - STATELESS
                            .invalidSessionUrl("/login")
                            .maximumSessions(1)
                            .expiredUrl("/login")
                            .sessionRegistry(sessionRegistry());

                    ses.sessionFixation()

                            .migrateSession(); //La sesión crea un Id de sesión para proteger de ataques
                    // migrateSession - newSession - none

                })
//                .httpBasic(Customizer.withDefaults())                 // Autentificación básica no recomendada
                .build();
    }


    public AuthenticationSuccessHandler successHandler() {
        return ((request, response, authentication) -> {

            response.sendRedirect("/v1/session");
        });
    }
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

}


