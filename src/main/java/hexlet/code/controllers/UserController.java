package hexlet.code.controllers;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    public static final String USER_CONTROLLER_PATH = "/users";
    public static final String ID = "/{id}";

}
