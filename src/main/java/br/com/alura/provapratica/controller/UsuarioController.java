package br.com.alura.provapratica.controller;


import br.com.alura.provapratica.dto.UsuarioRequestDTO;
import br.com.alura.provapratica.dto.UsuarioResponseDTO;
import br.com.alura.provapratica.mappers.UsuarioMapper;
import br.com.alura.provapratica.model.Usuario;
import br.com.alura.provapratica.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;


    public UsuarioController(UsuarioService usuarioService, UsuarioMapper usuarioMapper) {
        this.usuarioService = usuarioService;
        this.usuarioMapper = usuarioMapper;
    }

    @PostMapping
    public ResponseEntity <UsuarioResponseDTO> criarUsuario(@RequestBody @Valid UsuarioRequestDTO dto){
        Usuario dadosUsuario = usuarioMapper.UsuarioDTOparausuario(dto);
        Usuario novoUsuario = usuarioService.criarUsuario(dadosUsuario);
        UsuarioResponseDTO response = usuarioMapper.usuarioParaUsuarioDTO(novoUsuario);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(novoUsuario.getId()).toUri();
        return ResponseEntity.created(uri).body(response);

    }
    @GetMapping("/{id}")
    public ResponseEntity <UsuarioResponseDTO> buscarUsuario(@PathVariable Long id){
        Usuario buscarUsuario = usuarioService.buscarUsuario(id);
        UsuarioResponseDTO usuario = usuarioMapper.usuarioParaUsuarioDTO(buscarUsuario);
        return ResponseEntity.ok(usuario);

    }

    @GetMapping
    public ResponseEntity <Page<UsuarioResponseDTO>> listarUsuarios(@PageableDefault(size = 10,page = 0)Pageable pagina){
        Page<Usuario> listarUsuario = usuarioService.listarUsuario(pagina);
        Page<UsuarioResponseDTO>listadto = listarUsuario.map(usuarioMapper::usuarioParaUsuarioDTO);
        return ResponseEntity.ok(listadto);
    }

    @PutMapping("/{id}")
    public ResponseEntity <UsuarioResponseDTO> atualizarUsuario(@PathVariable Long id,@RequestBody @Valid UsuarioRequestDTO dto){
        Usuario dados = usuarioMapper.UsuarioDTOparausuario(dto);
        Usuario atualizarUsuarios= usuarioService.AtualizarUsuario(id, dados);
        UsuarioResponseDTO responseDTO = usuarioMapper.usuarioParaUsuarioDTO(atualizarUsuarios);
        return  ResponseEntity.ok(responseDTO);

    }

    @DeleteMapping("/{id}")
    public  ResponseEntity <Void> deletarUsuario(@PathVariable Long id){
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();

    }




}
