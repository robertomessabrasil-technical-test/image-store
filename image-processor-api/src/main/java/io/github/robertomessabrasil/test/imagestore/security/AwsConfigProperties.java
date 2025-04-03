package io.github.robertomessabrasil.test.imagestore.security;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "aws")
public class AwsConfigProperties {
    private String accessKey;
    private String secretKey;
    private String region;
    private String bucket;
    private String topicArn;
    private String queueName;
}