package io.github.robertomessabrasil.test.imagestore;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import io.github.robertomessabrasil.test.imagestore.security.ConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ImageListener {

    private static final String SQS_QUEUE = "robertomessabrasil-test-image-store";

    @Autowired
    ConfigProperties config;

    @Autowired
    AmazonSQS sqsClient;

    private static final Logger logger = Logger.getLogger(ImageListener.class.getName());

    @Scheduled(fixedDelay = 2000)
    public void consumeMessages() {
        try {
            String queueUrl = sqsClient.getQueueUrl(SQS_QUEUE).getQueueUrl();

            ReceiveMessageResult receiveMessageResult = sqsClient.receiveMessage(queueUrl);

            if (!receiveMessageResult.getMessages().isEmpty()) {
                com.amazonaws.services.sqs.model.Message message = receiveMessageResult.getMessages().get(0);
                logger.info(getMessage(message.getBody()));
                sqsClient.deleteMessage(queueUrl, message.getReceiptHandle());
            }

        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }
// "Message" : "cachorro.png:50",
    private String getMessage(String messageBody) {
        Pattern pattern = Pattern.compile("\"Message\" : \"(.*)\",");
        Matcher matcher = pattern.matcher(messageBody);
        if(matcher.find()) {
            return matcher.group(1);
        }
        return "No message line";
    }

}
