package br.com.alura.provapratica.service;

import br.com.alura.provapratica.model.Usuario;
import br.com.alura.provapratica.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioService {
    private UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public Usuario criarUsuario(Usuario usuario){
        if (usuarioRepository.existsByEmail(usuario.getEmail())){
            throw new RuntimeException("Email já cadastrado");
        }
        return usuarioRepository.save(usuario);
    }

    @Transactional(readOnly = true)
    public Usuario buscarUsuario(Long id){
        return usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    @Transactional(readOnly = true)
    public Page<Usuario> listarUsuario(Pageable paginacao){
        return usuarioRepository.findAll(paginacao);
    }

    @Transactional
    public void deletarUsuario(Long id){
        if(usuarioRepository.existsById(id)){
            usuarioRepository.deleteById(id);
        }else {
            throw new RuntimeException("Usuário não existe");
        }
    }

    @Transactional
    public Usuario AtualizarUsuario(Long id, Usuario dados ){
        Usuario dadosUsuario = usuarioRepository.findById(id).orElseThrow(()->new RuntimeException("Usuário não encontrado"));
        dadosUsuario.setNome(dados.getNome());
        dadosUsuario.setNumero(dados.getNumero());
        return usuarioRepository.save(dadosUsuario);
    }
}