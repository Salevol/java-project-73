package hexlet.code.service;

import hexlet.code.dto.UserDTO;
import hexlet.code.model.User;

import java.util.List;

public interface UserService {

    User getUserById(long id);

    List<User> getAllUsers();

    User createNewUser(UserDTO userDTO);

    User updateUser(long id, UserDTO userDTO);

    void deleteUser(long id);
}
