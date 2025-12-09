package david.git_projects_api.services;

import david.git_projects_api.domain.User;
import david.git_projects_api.dtos.UserDto;
import david.git_projects_api.repositories.UserRepositorty;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public  class UserService {
    private final UserRepositorty userRepositorty;
    private
    public UserService(UserRepositorty userRepositorty) {
        this.userRepositorty = userRepositorty;
    }

    public User getOrCreateUser(UserDto userDto){
        User user= userRepositorty.getUserById(userDto.id());
        if (user == null){
            user = userDto.toUser();
            userRepositorty.save(user);
        }
        return user;
    }
}