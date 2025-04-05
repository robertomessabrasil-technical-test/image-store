package io.github.robertomessabrasil.test.imagestore.controller.user.dto;

import io.github.robertomessabrasil.test.imagestore.model.RoleName;

public record UserDto(

        String email,
        RoleName role

) {
}
