package io.github.robertomessabrasil.test.imagestore.controller.image;

public record ResizeImageDto(
        String fileName,
        int percentage
) {
}
