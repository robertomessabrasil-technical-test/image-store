package io.github.robertomessabrasil.test.imagestore.service.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import io.github.robertomessabrasil.test.imagestore.security.ConfigProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class BucketService {

    Logger logger = LogManager.getLogger(BucketService.class);

    @Autowired
    ConfigProperties config;

    @Autowired
    AmazonS3 s3Client;

    public List<String> listObjectsInBucket(String bucketName) {
        ObjectListing objectListing = s3Client.listObjects(bucketName);
        return objectListing.getObjectSummaries()
                .stream()
                .map(S3ObjectSummary::getKey)
                .collect(Collectors.toList());
    }

    public void getObjectFromBucket(String bucketName, String objectName) throws IOException {
        S3Object s3Object = s3Client.getObject(bucketName, objectName);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        File file = new File(config.getUploadFolder() + "/" + objectName);
        file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        byte[] read_buf = new byte[1024];
        int read_len = 0;
        while ((read_len = inputStream.read(read_buf)) > 0) {
            fos.write(read_buf, 0, read_len);
        }
        inputStream.close();
        fos.close();

    }

    public void pubObjectIntoBucket(String bucketName, String objectName, String filePathToUpload) {
        s3Client.putObject(bucketName, objectName, new File(filePathToUpload));
    }

    public void pubObjectIntoBucket(MultipartFile file, String bucketName, String objectName) throws IOException {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.setContentLength(file.getSize());
        s3Client.putObject(bucketName, objectName, file.getInputStream(), objectMetadata);
    }

}
