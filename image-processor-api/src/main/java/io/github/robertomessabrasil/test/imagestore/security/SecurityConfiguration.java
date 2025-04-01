package io.github.robertomessabrasil.test.imagestore.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    UserAuthenticationFilter userAuthenticationFilter;

    public static final String[] NO_AUTHENTICATION_ENDPOINT = {
            "/image-user/login",
            "/image-user"
    };

    public static final String[] AUTHENTICATION_REQUIRED_ENDPOINT = {
            "/image/list",
            "/image/upload",
            "/image/download/**"
    };

    public static final String[] ADMIN_ENDPOINT = {
            "/admin/users"
    };

    @Bean
//    @SuppressWarnings("removal")
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

//        SecurityFilterChain securityFilterChain = httpSecurity.csrf().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and().authorizeHttpRequests()
//                .requestMatchers(NO_AUTHENTICATION_ENDPOINT).permitAll()
//                .requestMatchers(AUTHENTICATION_REQUIRED_ENDPOINT).authenticated()
//                .requestMatchers(ADMIN_ENDPOINT).hasRole("ADMINISTRATOR")
//                .anyRequest().denyAll()
//                .and().addFilterBefore(userAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
//                .build();

        SecurityFilterChain securityFilterChain = httpSecurity
                .cors(c -> c.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(NO_AUTHENTICATION_ENDPOINT).permitAll()
                        .requestMatchers(AUTHENTICATION_REQUIRED_ENDPOINT).authenticated()
                        .requestMatchers(ADMIN_ENDPOINT).hasRole("ADMINISTRATOR")
                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.disable())
                .addFilterBefore(userAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

//        SecurityFilterChain securityFilterChain = httpSecurity
//                .csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(authorize -> authorize
//                        .anyRequest().permitAll())
//                .csrf(AbstractHttpConfigurer::disable)
//                .build();

        return securityFilterChain;

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
