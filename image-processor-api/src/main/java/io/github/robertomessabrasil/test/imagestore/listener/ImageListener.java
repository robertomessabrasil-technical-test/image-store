package io.github.robertomessabrasil.test.imagestore.listener;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import io.github.robertomessabrasil.test.imagestore.security.AwsConfigProperties;
import io.github.robertomessabrasil.test.imagestore.service.image.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ImageListener {

    @Autowired
    private AwsConfigProperties awsConfig;

    @Autowired
    private AmazonSQS sqsClient;

    @Autowired
    private ImageService imageService;

    private static final Logger logger = Logger.getLogger(ImageListener.class.getName());

    @Scheduled(fixedDelay = 2000)
    public void consumeMessages() {

        try {

            String queueUrl = sqsClient.getQueueUrl(awsConfig.getQueueName()).getQueueUrl();

            ReceiveMessageResult receiveMessageResult = sqsClient.receiveMessage(queueUrl);

            if (receiveMessageResult.getMessages().isEmpty()) {
                return;
            }

            receiveMessageResult.getMessages().forEach(sqsMessage -> {

                String message = getMessage(sqsMessage.getBody());

                String[] messageParts = message.split("\\:");

                try {
                    imageService.resizeImage(messageParts[0], Integer.parseInt(messageParts[1]));
                } catch (IOException e) {
                    logger.log(Level.ALL, e.getMessage());
                }

                sqsClient.deleteMessage(queueUrl, sqsMessage.getReceiptHandle());

            });


        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }

    private String getMessage(String messageBody) {
        Pattern pattern = Pattern.compile("\"Message\" : \"(.*)\",");
        Matcher matcher = pattern.matcher(messageBody);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "No message line";
    }

}
