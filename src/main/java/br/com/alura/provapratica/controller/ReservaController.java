package br.com.alura.provapratica.controller;

import br.com.alura.provapratica.dto.ReservaRequestDTO;
import br.com.alura.provapratica.dto.ReservaResponseDTO;
import br.com.alura.provapratica.mappers.ReservaMapper;
import br.com.alura.provapratica.model.Reserva;
import br.com.alura.provapratica.service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;


@RestController
@RequestMapping("/api/v1/reservas")
public class ReservaController {

    private final ReservaService reservaService;
    private final ReservaMapper reservaMapper;

    public ReservaController(ReservaService reservaService, ReservaMapper reservaMapper) {
        this.reservaService = reservaService;
        this.reservaMapper = reservaMapper;
    }

    @PostMapping
    public ResponseEntity<ReservaResponseDTO> criarReserva(@RequestBody @Valid ReservaRequestDTO dto){
        Reserva dados = reservaMapper.reservaDTOparaReserva(dto);
        Reserva criarReserva = reservaService.criarReserva(dados);
        ReservaResponseDTO responseDTO = reservaMapper.reservaParaReservaDTO(criarReserva);

        // Dica: Ajustei getReservaId() para getId() assumindo o padrão, mude se sua entidade for diferente!
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(criarReserva.getReservaId()).toUri();
        return ResponseEntity.created(uri).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> buscarReserva(@PathVariable Long id){
        Reserva buscarReserva = reservaService.buscarReserva(id);
        ReservaResponseDTO response = reservaMapper.reservaParaReservaDTO(buscarReserva);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<ReservaResponseDTO>> listarReservas(@PageableDefault(size = 10, page = 0) Pageable paginacao) {
        // 1. O service agora retorna um Page<Reserva>
        Page<Reserva> reservas = reservaService.listarReservas(paginacao);

        // 2. Usamos o .map() para converter cada item da página usando o mapper
        Page<ReservaResponseDTO> listaDTO = reservas.map(reservaMapper::reservaParaReservaDTO);

        // 3. Retornamos a página de DTOs
        return ResponseEntity.ok(listaDTO);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> atualizarReserva(@PathVariable Long id, @RequestBody @Valid ReservaRequestDTO dto){
        Reserva dadosReserva = reservaMapper.reservaDTOparaReserva(dto);
        Reserva atualizarReserva = reservaService.atualizarReserva(dadosReserva, id);
        ReservaResponseDTO respostaReserva = reservaMapper.reservaParaReservaDTO(atualizarReserva);
        return ResponseEntity.ok(respostaReserva);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarReserva(@PathVariable Long id){
        reservaService.cancelarReserva(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/busca")
    public ResponseEntity<Page<ReservaResponseDTO>> buscarPorSalaEPeriodo(
            @RequestParam Long salaId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim,
            @PageableDefault(size = 10, page = 0) Pageable paginacao) {

        Page<Reserva> reservas = reservaService.listarPorSalaEPeriodo(salaId, inicio, fim, paginacao);
        Page<ReservaResponseDTO> listaDTO = reservas.map(reservaMapper::reservaParaReservaDTO);

        return ResponseEntity.ok(listaDTO);
    }
}