package com.matawanservices.ogcnice.dtos;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record TeamDTO(
        @NotEmpty(message = "Le nom de l'équipe ne peut pas être vide.") String name,
        @NotEmpty(message = "L'acronyme de l'équipe ne peut pas être vide.") String acronym,
        @Min(value = 0, message = "Le budget doit être un nombre positif") Double budget,
        List<PlayerDTO> players // Liste de joueurs associée à l'équipe
) {
}
