package br.com.alura.provapratica.mappers;

import br.com.alura.provapratica.dto.UsuarioRequestDTO;
import br.com.alura.provapratica.dto.UsuarioResponseDTO;
import br.com.alura.provapratica.model.Usuario;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    UsuarioResponseDTO usuarioParaUsuarioDTO(Usuario usuario);
    Usuario UsuarioDTOparausuario(UsuarioRequestDTO dto);
    List<UsuarioResponseDTO>listaUsuriosDTO(List<Usuario> listaDeUsuariosDTO);
}
