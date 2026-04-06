package br.com.alura.provapratica.controller;


import br.com.alura.provapratica.dto.SalaRequestDTO;
import br.com.alura.provapratica.dto.SalaResponseDTO;
import br.com.alura.provapratica.mappers.SalaMapper;
import br.com.alura.provapratica.model.Sala;
import br.com.alura.provapratica.service.SalaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/api/v1/salas")
public class SalaController {

    private final SalaService salaService;
    private final SalaMapper salaMapper;


    public SalaController(SalaService salaService, SalaMapper salaMapper) {
        this.salaService = salaService;
        this.salaMapper = salaMapper;
    }

    @PostMapping
    public ResponseEntity <SalaResponseDTO> criarSala(@RequestBody @Valid SalaRequestDTO dto){
        Sala dados = salaMapper.SalaDTOparasala(dto);
        Sala criarSala = salaService.criarSala(dados);
        SalaResponseDTO responseDTO = salaMapper.salaParaSalaDTO(criarSala);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(criarSala.getId()).toUri();
        return ResponseEntity.created(uri).body(responseDTO);

    }

    @GetMapping("/{id}")
    public ResponseEntity <SalaResponseDTO> buscarSala (@PathVariable Long id){
        Sala buscarSala = salaService.buscarSala(id);
        SalaResponseDTO sala = salaMapper.salaParaSalaDTO(buscarSala);
        return ResponseEntity.ok(sala);
    }

    @GetMapping
    public ResponseEntity <Page<SalaResponseDTO>> listarSala(@PageableDefault(size = 10 , page = 0)Pageable pagina){
        Page<Sala> sala = salaService.listarSalas(pagina);
        Page<SalaResponseDTO> lista = sala.map(salaMapper::salaParaSalaDTO);
        return ResponseEntity.ok(lista);
    }

    @PutMapping ("/{id}")
    public ResponseEntity <SalaResponseDTO> atualizarSala(@PathVariable Long id, @RequestBody @Valid SalaRequestDTO sala){
        Sala dadosSala = salaMapper.SalaDTOparasala(sala);
        Sala atualizarSala=salaService.atualizarSala(id ,dadosSala);
        SalaResponseDTO respostaSala = salaMapper.salaParaSalaDTO(atualizarSala);
        return ResponseEntity.ok(respostaSala);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarSala(@PathVariable Long id){
        salaService.excluirSala(id);
        return ResponseEntity.noContent().build();

    }






}
