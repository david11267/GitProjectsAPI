package david.git_projects_api.dtos;

import org.springframework.security.oauth2.jwt.Jwt;

import java.time.Instant;

public record ClerkUserDto (String username, String email, Instant exp, Instant iat ){


    public static ClerkUserDto jwtToDto(Jwt jwt){
        String username = jwt.getClaim("username");
        String email = jwt.getClaim("email");
        Instant experation = jwt.getClaim("exp");
        Instant issuedAt = jwt.getClaim("iat");
        return new ClerkUserDto(username, email, experation, issuedAt);
    }

    public static ClerkUserDto toDto(String username, String email, Instant exp, Instant iat){
        return new ClerkUserDto(username, email, exp, iat);
    }
}
