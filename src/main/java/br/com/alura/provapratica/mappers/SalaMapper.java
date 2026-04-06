package br.com.alura.provapratica.mappers;

import br.com.alura.provapratica.dto.SalaRequestDTO;
import br.com.alura.provapratica.dto.SalaResponseDTO;

import br.com.alura.provapratica.model.Sala;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper (componentModel = "spring")
public interface SalaMapper {
    SalaResponseDTO salaParaSalaDTO(Sala sala);
    Sala SalaDTOparasala(SalaRequestDTO dto);
    List<SalaResponseDTO> listaSalaDTO (List<Sala> listaSalaDTO);

}