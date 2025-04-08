package io.github.robertomessabrasil.test.imagestore.security;

import io.github.robertomessabrasil.test.imagestore.model.User;
import io.github.robertomessabrasil.test.imagestore.model.UserRepository;
import io.github.robertomessabrasil.test.imagestore.service.security.JwtTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Component
public class UserAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {

        boolean isOptions = (req.getMethod().equals("OPTIONS"));
        if (isOptions) {
            filterChain.doFilter(req, res);
            return;
        }

        boolean isEndpointPublic = isEndpointPublic(req);
        if (isEndpointPublic) {
            filterChain.doFilter(req, res);
            return;
        }

        Optional<String> token = getToken(req);
        if (token.isEmpty()) {
            throw new RuntimeException("No token present");
        }

        String subject = jwtTokenService.getSubjectFromToken(token.orElse(null));
        User user = userRepository.findByEmail(subject).get();
        UserDetailsImpl userDetails = new UserDetailsImpl(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(req, res);

    }

    private Optional<String> getToken(HttpServletRequest req) {

        Optional<String> authHeader = Optional.ofNullable(req.getHeader("Authorization"));
        if (authHeader.isEmpty()) {
            return authHeader;
        }

        String authToken = authHeader.get().replace("Bearer ", "");
        return Optional.of(authToken);

    }

    private boolean isEndpointPublic(HttpServletRequest req) {

        String uri = req.getRequestURI();
        boolean isEndpointPublic = Arrays.asList(SecurityConfiguration.NO_AUTHENTICATION_ENDPOINT).contains(uri);
        if (isEndpointPublic) {
            return true;
        }
        boolean isSwagger = uri.startsWith("/v3/api-docs") || uri.startsWith("/swagger-ui");
        if (isSwagger) {
            return true;
        }
        return false;
    }

}
