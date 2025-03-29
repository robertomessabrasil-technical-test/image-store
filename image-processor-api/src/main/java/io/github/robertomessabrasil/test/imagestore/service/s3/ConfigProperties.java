package io.github.robertomessabrasil.test.imagestore.service.s3;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "aws")
public class ConfigProperties {
    private String accessKey;
    private String secretKey;
    private String region;
    private String bucket;
    private String uploadFolder;
}
