package io.github.robertomessabrasil.test.imagestore.controller.image;

import io.github.robertomessabrasil.test.imagestore.security.LocalConfigProperties;
import io.github.robertomessabrasil.test.imagestore.service.s3.BucketService;
import io.github.robertomessabrasil.test.imagestore.security.AwsConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/image-store")
public class S3Controller {

    @Autowired
    private AwsConfigProperties awsConfig;
    @Autowired
    private LocalConfigProperties localConfig;

    @Autowired
    private BucketService bucketService;

    @GetMapping
    ResponseEntity<List<String>> getFileList() {
        List<String> objectsInBucket = bucketService.listObjectsInBucket(awsConfig.getBucket());
        return ResponseEntity.ok(objectsInBucket);
    }

    @PostMapping("/upload")
    ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) throws IOException {
        bucketService.pubObjectIntoBucket(file, awsConfig.getBucket(), file.getOriginalFilename());
        return ResponseEntity.ok(file.getOriginalFilename());
    }

    @GetMapping(value = "/download/{fileName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public FileSystemResource getFile(@PathVariable("fileName") String fileName) throws IOException {
        bucketService.getObjectFromBucket(awsConfig.getBucket(), fileName);
        return new FileSystemResource(localConfig.getUploadFolder() + "/" + fileName);
    }

}
