package br.com.alura.provapratica.repository;

import br.com.alura.provapratica.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario , Long> {

    boolean existsByEmail(String email);


}
