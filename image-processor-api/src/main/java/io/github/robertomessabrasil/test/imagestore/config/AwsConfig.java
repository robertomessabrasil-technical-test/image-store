package io.github.robertomessabrasil.test.imagestore.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import io.github.robertomessabrasil.test.imagestore.security.AwsConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsConfig {

    @Autowired
    AwsConfigProperties config;

    @Bean
    public AmazonS3 s3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(config.getAccessKey(), config.getSecretKey());
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(config.getRegion())
                .build();
    }

    @Bean
    public AmazonSNSClient snsClient() {
        return (AmazonSNSClient) AmazonSNSClientBuilder.standard()
                .withRegion(config.getRegion())
                .withCredentials(
                        new AWSStaticCredentialsProvider(
                                new BasicAWSCredentials(config.getAccessKey(), config.getSecretKey())
                        )
                )
                .build();
    }

    @Bean
    public AmazonSQS sqsClient() {
        return (AmazonSQS) AmazonSQSClientBuilder.standard()
                .withRegion(config.getRegion())
                .withCredentials(
                        new AWSStaticCredentialsProvider(
                                new BasicAWSCredentials(config.getAccessKey(), config.getSecretKey())
                        )
                )
                .build();
    }

}
