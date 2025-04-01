package io.github.robertomessabrasil.test.imagestore.controller.user;

import io.github.robertomessabrasil.test.imagestore.controller.user.dto.CreateUserDto;
import io.github.robertomessabrasil.test.imagestore.controller.user.dto.LoginUserDto;
import io.github.robertomessabrasil.test.imagestore.controller.user.dto.RecoveryJwtTokenDto;
import io.github.robertomessabrasil.test.imagestore.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/image-user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<RecoveryJwtTokenDto> authenticateUser(@RequestBody LoginUserDto loginUserDto) {
        RecoveryJwtTokenDto tokenDto = userService.authenticateUser(loginUserDto);
        return ResponseEntity.ok(tokenDto);
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody CreateUserDto createUserDto) {
        userService.createUser(createUserDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
