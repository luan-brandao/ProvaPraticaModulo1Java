package br.com.alura.provapratica.repository;

import br.com.alura.provapratica.model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaRepository extends JpaRepository<Sala,Long> {
    boolean existsByNumeroSala(int numeroSala);
}
