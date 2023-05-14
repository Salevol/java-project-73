package hexlet.code.service;

import hexlet.code.dto.UserDto;
import hexlet.code.model.User;

import java.util.List;

public interface UserService {

    User getUserById(long id);

    List<User> getAllUsers();

    User createNewUser(UserDto userDTO);

    User updateUser(long id, UserDto userDto);

    void deleteUser(long id);

    String getCurrentUserName();

    User getCurrentUser();
}
