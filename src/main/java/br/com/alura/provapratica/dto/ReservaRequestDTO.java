package br.com.alura.provapratica.dto;

import br.com.alura.provapratica.model.StatusReserva;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record ReservaRequestDTO(
        @NotNull(message = "O ID do usuário é obrigatório")
        Long usuarioId,

        @NotNull(message = "O ID da sala é obrigatório")
        Long salaId,

        @NotNull(message = "O horário de início é obrigatório")
        @Future(message = "O horário de início deve ser uma data futura")
        LocalDateTime horarioInicio,

        @NotNull(message = "O horário final é obrigatório")
        @Future(message = "O horário final deve ser uma data futura")
        LocalDateTime horarioFinal,

        @NotNull(message = "O status da reserva é obrigatório")
        StatusReserva status
) {
}