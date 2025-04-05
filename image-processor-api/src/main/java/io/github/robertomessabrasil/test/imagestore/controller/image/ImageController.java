package io.github.robertomessabrasil.test.imagestore.controller.image;

import io.github.robertomessabrasil.test.imagestore.service.image.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("image-handler")
public class ImageController {

    @Autowired
    ImageService imageService;

    @PostMapping
    public ResponseEntity resizeImage(@RequestBody ResizeImageDto resizeImageDto) {

        imageService.sendResizeImageNotification(resizeImageDto.fileName(), resizeImageDto.percentage());

        return new ResponseEntity(HttpStatus.NO_CONTENT);

    }

}
