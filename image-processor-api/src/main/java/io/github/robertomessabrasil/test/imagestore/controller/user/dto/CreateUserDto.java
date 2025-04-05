package io.github.robertomessabrasil.test.imagestore.controller.user.dto;

import io.github.robertomessabrasil.test.imagestore.model.RoleName;

public record CreateUserDto(

        String email,
        String password,
        RoleName role

) {
}
