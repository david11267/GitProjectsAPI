package david.git_projects_api.dtos;

import david.git_projects_api.domain.User;
import org.springframework.security.oauth2.jwt.Jwt;
public record UserDto(String id, String profileImage,String username, String name, String surname, String email ){

    public static UserDto jwtToDto(Jwt jwt){
        String id = jwt.getClaim("sub");
        String profileImage = jwt.getClaim("profileImage");
        String username = jwt.getClaim("username");
        String name = jwt.getClaim("name");
        String surname = jwt.getClaim("surname");
        String email = jwt.getClaim("email");
        return new UserDto(id,profileImage,username,name,surname,email);
    }

    public User toUser() {
        return new User(id,profileImage,username,name,surname,email);
    }
}
