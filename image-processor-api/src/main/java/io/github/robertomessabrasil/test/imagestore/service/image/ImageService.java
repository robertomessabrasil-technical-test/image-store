package io.github.robertomessabrasil.test.imagestore.service.image;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import io.github.robertomessabrasil.test.imagestore.security.AwsConfigProperties;
import io.github.robertomessabrasil.test.imagestore.security.LocalConfigProperties;
import io.github.robertomessabrasil.test.imagestore.service.s3.BucketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Service
public class ImageService {

    @Autowired
    private AwsConfigProperties awsConfig;
    @Autowired
    private LocalConfigProperties localConfig;

    @Autowired
    AmazonSNSClient snsClient;

    @Autowired
    private BucketService bucketService;

    public void sendResizeImageNotification(String fileName, int percentage) {

        String content = fileName + ":" + percentage;

        PublishRequest publishRequest = new PublishRequest();

        publishRequest.setTargetArn(awsConfig.getTopicArn());
        publishRequest.setMessage(content);
        publishRequest.setSubject("Resize");

        snsClient.publish(publishRequest);

    }

    public void resizeImage(String fileName, int percentage) throws IOException {

        bucketService.getObjectFromBucket(awsConfig.getBucket(), fileName);

        File file = new File(localConfig.getUploadFolder() + "/" + fileName);

        BufferedImage bufferedImage = ImageIO.read(file);

        int height = bufferedImage.getHeight();
        int width = bufferedImage.getWidth();
        int newWidth = Math.round(width * percentage / 100);
        int newHeight = Math.round(height * percentage / 100);

        Image image = bufferedImage.getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT);

        BufferedImage uploadImage = convertToBufferedImage(image);

        ImageIO.write(uploadImage, "png", file);

        bucketService.pubObjectIntoBucket(awsConfig.getBucket(), fileName, file.getAbsolutePath());

    }

    public static BufferedImage convertToBufferedImage(Image img) {

        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        BufferedImage bi = new BufferedImage(
                img.getWidth(null), img.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);

        Graphics2D graphics2D = bi.createGraphics();
        graphics2D.drawImage(img, 0, 0, null);
        graphics2D.dispose();

        return bi;
    }

}
