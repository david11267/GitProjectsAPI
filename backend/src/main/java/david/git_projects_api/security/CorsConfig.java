package david.git_projects_api.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
public class CorsConfig {

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Bean
    public CorsConfigurationSource corsConfigurationSource(Environment environment) {
        CorsConfiguration config = new CorsConfiguration();
        // DO NOT use "*" if you will allow credentials. Use exact origin(s).
        // Always allow production frontend
        config.setAllowedOrigins(Arrays.asList(
                environment.getProperty("FrontendUrl")
        ));

        // Only add localhost origins in dev mode
        if ("dev".equals(activeProfile)) {
            config.getAllowedOrigins().addAll(Arrays.asList(
                    "http://localhost:3000",
                    "http://localhost:5173"
            ));
        }        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // apply to all paths; change if you want more restrictive scope
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}