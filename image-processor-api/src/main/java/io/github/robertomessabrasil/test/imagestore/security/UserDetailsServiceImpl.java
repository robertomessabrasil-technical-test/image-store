package io.github.robertomessabrasil.test.imagestore.security;

import io.github.robertomessabrasil.test.imagestore.model.User;
import io.github.robertomessabrasil.test.imagestore.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Supplier<RuntimeException> lambda = () -> new RuntimeException("User not found");
        User user = userRepository.findByEmail(username).orElseThrow(lambda);
        return new UserDetailsImpl(user);

    }
}
