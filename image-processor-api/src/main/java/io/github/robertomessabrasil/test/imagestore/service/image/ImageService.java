package io.github.robertomessabrasil.test.imagestore.service.image;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import io.github.robertomessabrasil.test.imagestore.security.ConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

    @Autowired
    ConfigProperties config;

    @Autowired
    AmazonSNSClient snsClient;

    public void resizeImage(String fileName, int percentage) {

        String content = fileName + ":" + percentage;

        PublishRequest publishRequest = new PublishRequest();

        publishRequest.setTargetArn(config.getTopicArn());
        publishRequest.setMessage(content);
        publishRequest.setSubject("Resize");

        snsClient.publish(publishRequest);

    }
}
