package br.com.alura.provapratica.dto;

import br.com.alura.provapratica.model.StatusReserva;
import java.time.LocalDateTime;

public record ReservaResponseDTO(
        Long reservaId,
        UsuarioResponseDTO usuario, // Usando o DTO de saída do usuário!
        SalaResponseDTO sala,       // Usando o DTO de saída da sala!
        LocalDateTime horarioInicio,
        LocalDateTime horarioFinal,
        StatusReserva status
) {
}