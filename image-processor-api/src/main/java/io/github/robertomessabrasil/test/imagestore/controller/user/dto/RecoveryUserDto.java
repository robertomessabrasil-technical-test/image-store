package io.github.robertomessabrasil.test.imagestore.controller.user.dto;

import io.github.robertomessabrasil.test.imagestore.model.Role;

public record RecoveryUserDto(

        Long id,
        String email,
        Role role

) {
}
