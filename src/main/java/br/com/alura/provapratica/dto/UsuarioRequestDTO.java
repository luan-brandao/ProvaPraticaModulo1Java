package br.com.alura.provapratica.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioRequestDTO(
        @NotBlank(message = "O nome é obrigatório")
        String nome,

        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "O formato do e-mail é inválido")
        String email,

        @NotBlank(message = "O número de telefone é obrigatório")
        String numero
) {
}