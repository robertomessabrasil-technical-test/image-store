package io.github.robertomessabrasil.test.imagestore.security;

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
    private String topicArn;
    private String queueArn;
}
//arn:aws:sqs:us-east-1:471112695359:robertomessabrasil-test-image-store
//arn:aws:sns:us-east-1:471112695359:robertomessabrasil-test-image-store
//arn:aws:sns:us-east-1:471112695359:robertomessabrasil-test-image-store:9990b28f-f141-4cdf-94f3-343288dea51f