package hexlet.code.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "welcome-controller", description = "Welcome")
@RestController
public class WelcomeController {
    @Operation(summary = "Welcome path")
    @ApiResponse(responseCode = "200")
    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to Spring";
    }

}
