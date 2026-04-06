package br.com.alura.provapratica.repository;

import br.com.alura.provapratica.model.Reserva;
import br.com.alura.provapratica.model.Sala;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

import java.util.List;


@Repository
public interface ReservaRepository extends JpaRepository<Reserva,Long> {
    @Query("""
    SELECT COUNT(r) > 0 
    FROM Reserva r 
    WHERE r.sala = :sala 
    AND r.status != 'CANCELADA' 
    AND (:inicio < r.horarioFinal AND :fim > r.horarioInicio)
    AND (:id IS NULL OR r.reservaId != :id)
    """)
    boolean existeConflito(@Param("sala") Sala sala,
                           @Param("inicio") LocalDateTime inicio,
                           @Param("fim") LocalDateTime fim,
                           @Param("id") Long id);

    Page<Reserva> findBySalaAndHorarioInicioBetween(
            Sala sala,
            LocalDateTime inicio,
            LocalDateTime fim,
            Pageable paginacao
    );
}
