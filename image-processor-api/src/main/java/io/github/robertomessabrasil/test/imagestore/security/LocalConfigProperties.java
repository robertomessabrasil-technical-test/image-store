package io.github.robertomessabrasil.test.imagestore.security;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "local")
public class LocalConfigProperties {
    private String uploadFolder;
}