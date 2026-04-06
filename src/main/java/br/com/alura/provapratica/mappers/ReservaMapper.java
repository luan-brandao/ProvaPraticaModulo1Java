package br.com.alura.provapratica.mappers;

import br.com.alura.provapratica.dto.ReservaRequestDTO;
import br.com.alura.provapratica.dto.ReservaResponseDTO;
import br.com.alura.provapratica.model.Reserva;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReservaMapper {

    // Avisando o MapStruct para pegar o usuarioId do DTO e colocar no id do Usuario da Entidade
    @Mapping(source = "usuarioId", target = "usuario.id")
    @Mapping(source = "salaId", target = "sala.id")
    Reserva reservaDTOparaReserva(ReservaRequestDTO dto);

    ReservaResponseDTO reservaParaReservaDTO(Reserva reserva);

    List<ReservaResponseDTO> listaReservaDTO(List<Reserva> reservas);
}