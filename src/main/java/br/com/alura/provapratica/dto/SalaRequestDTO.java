package br.com.alura.provapratica.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record SalaRequestDTO(
        @NotNull(message = "O número da sala é obrigatório")
        Integer numeroSala,

        @NotNull(message = "A quantidade de pessoas é obrigatória")
        @Min(value = 1, message = "A capacidade mínima deve ser de 1 pessoa")
        Integer quantidadePessoas,

        @NotNull(message = "O status da sala é obrigatório")
        Boolean statusSala
) {
}