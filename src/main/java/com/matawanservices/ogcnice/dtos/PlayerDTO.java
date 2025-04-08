package com.matawanservices.ogcnice.dtos;

import jakarta.validation.constraints.NotEmpty;

public record PlayerDTO(
        @NotEmpty(message = "Le nom du joueur ne peut pas être vide.") String name,
        @NotEmpty(message = "La position du joueur ne peut pas être vide.") String position
) {
}