package br.com.alura.provapratica.service;

import br.com.alura.provapratica.model.Reserva;
import br.com.alura.provapratica.model.Sala;
import br.com.alura.provapratica.model.StatusReserva;
import br.com.alura.provapratica.model.Usuario;
import br.com.alura.provapratica.repository.ReservaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservaService {
    private final ReservaRepository reservaRepository;
    private final UsuarioService usuarioService;
    private final SalaService salaService;

    public ReservaService(ReservaRepository reservaRepository, UsuarioService usuarioService, SalaService salaService) {
        this.reservaRepository = reservaRepository;
        this.usuarioService = usuarioService;
        this.salaService = salaService;
    }

    @Transactional
    public Reserva criarReserva(Reserva reserva) {
        Usuario dadosUsuario = usuarioService.buscarUsuario(reserva.getUsuario().getId());
        Sala dadosSala = salaService.buscarSala(reserva.getSala().getId());

        if (!dadosSala.isStatusSala()) {
            throw new RuntimeException("Não é possível reservar uma sala inativa.");
        }

        // Passamos 'null' no ID porque é uma reserva nova
        boolean temConflito = reservaRepository.existeConflito(
                dadosSala, reserva.getHorarioInicio(), reserva.getHorarioFinal(), null
        );

        if (temConflito) {
            throw new RuntimeException("Conflito de horário detectado.");
        }

        reserva.setUsuario(dadosUsuario);
        reserva.setSala(dadosSala);
        return reservaRepository.save(reserva);
    }
    @Transactional(readOnly = true)
    public Reserva buscarReserva(Long id){
        return reservaRepository.findById(id).orElseThrow(() -> new RuntimeException("Reserva não encontrada"));
    }

    @Transactional(readOnly = true)
    public Page<Reserva> listarReservas(Pageable paginacao){
        return reservaRepository.findAll(paginacao);
    }

    @Transactional
    public void cancelarReserva(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada"));

        reserva.setStatus(StatusReserva.CANCELADA);
        reservaRepository.save(reserva);
    }

    @Transactional
    public Reserva atualizarReserva(Reserva novosDados, Long id) {
        Reserva reservaExistente = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada"));

        Sala dadosSala = salaService.buscarSala(novosDados.getSala().getId());
        Usuario dadosUsuario = usuarioService.buscarUsuario(novosDados.getUsuario().getId());

        if (!dadosSala.isStatusSala()) {
            throw new RuntimeException("Não é possível atualizar para uma sala inativa.");
        }

        // Agora usamos a mesma consulta do banco, passando o ID atual para ignorá-lo
        boolean temConflito = reservaRepository.existeConflito(
                dadosSala, novosDados.getHorarioInicio(), novosDados.getHorarioFinal(), id
        );

        if (temConflito) {
            throw new RuntimeException("Conflito de horário detectado.");
        }

        reservaExistente.setSala(dadosSala);
        reservaExistente.setUsuario(dadosUsuario);
        reservaExistente.setStatus(novosDados.getStatus());
        reservaExistente.setHorarioInicio(novosDados.getHorarioInicio());
        reservaExistente.setHorarioFinal(novosDados.getHorarioFinal());

        return reservaRepository.save(reservaExistente);
    }
    @Transactional(readOnly = true)
    public Page<Reserva> listarPorSalaEPeriodo(Long salaId, LocalDateTime inicio, LocalDateTime fim, Pageable paginacao) {
        // 1. Primeiro buscamos a entidade Sala pelo ID
        Sala sala = salaService.buscarSala(salaId);

        // 2. Chamamos o repositório com o filtro de período que você criou
        return reservaRepository.findBySalaAndHorarioInicioBetween(sala, inicio, fim, paginacao);
    }
}