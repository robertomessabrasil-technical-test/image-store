package io.github.robertomessabrasil.test.imagestore.service.user;

import io.github.robertomessabrasil.test.imagestore.controller.user.dto.CreateUserDto;
import io.github.robertomessabrasil.test.imagestore.controller.user.dto.LoginUserDto;
import io.github.robertomessabrasil.test.imagestore.controller.user.dto.RecoveryJwtTokenDto;
import io.github.robertomessabrasil.test.imagestore.model.Role;
import io.github.robertomessabrasil.test.imagestore.model.User;
import io.github.robertomessabrasil.test.imagestore.model.UserRepository;
import io.github.robertomessabrasil.test.imagestore.security.SecurityConfiguration;
import io.github.robertomessabrasil.test.imagestore.security.UserDetailsImpl;
import io.github.robertomessabrasil.test.imagestore.service.security.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityConfiguration securityConfiguration;

    public RecoveryJwtTokenDto authenticateUser(LoginUserDto loginUserDto) {

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginUserDto.email(), loginUserDto.password());

        Authentication authentication = authenticationManager.authenticate(token);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        RecoveryJwtTokenDto recoveryJwtTokenDto = new RecoveryJwtTokenDto(jwtTokenService.generateToken(userDetails));

        return recoveryJwtTokenDto;

    }

    public void createUser(CreateUserDto createUserDto) {

        String password = securityConfiguration.passwordEncoder().encode(createUserDto.password());

        Role role = Role.builder().name(createUserDto.role()).build();

        User user = User.builder()
                .email(createUserDto.email())
                .password(password)
                .role(role)
                .build();

        userRepository.save(user);

    }

}
