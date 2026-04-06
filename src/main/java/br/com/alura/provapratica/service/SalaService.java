package br.com.alura.provapratica.service;

import br.com.alura.provapratica.model.Sala;
import br.com.alura.provapratica.repository.SalaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SalaService  {
    private SalaRepository salaRepository;

    public SalaService(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }

    @Transactional
    public Sala criarSala(Sala sala){
        if (sala.getQuantidadePessoas() <= 0) {
            throw new RuntimeException("A capacidade da sala deve ser positiva.");
        }
        if (salaRepository.existsByNumeroSala(sala.getNumeroSala())){
            throw new RuntimeException("Sala já cadastrada no sistema");
        }
        return salaRepository.save(sala);
    }

    @Transactional(readOnly = true)
    public Sala buscarSala(Long id){
        return salaRepository.findById(id).orElseThrow(()-> new RuntimeException("Sala não encontrada"));
    }

    @Transactional(readOnly = true)
    public Page<Sala> listarSalas(Pageable paginacao){
        return salaRepository.findAll(paginacao);
    }

    @Transactional
    public void excluirSala(Long id){
        if (salaRepository.existsById(id)){
            salaRepository.deleteById(id);
        }else {
            throw new RuntimeException("Id não encontrado");
        }
    }

    @Transactional
    public Sala atualizarSala(Long id, Sala sala){
        if (sala.getQuantidadePessoas() <= 0) {
            throw new RuntimeException("A capacidade da sala deve ser positiva.");
        }
        Sala dadosSala = salaRepository.findById(id).orElseThrow(()-> new RuntimeException("Sala não encontrada"));

        dadosSala.setNumeroSala(sala.getNumeroSala());
        dadosSala.setQuantidadePessoas(sala.getQuantidadePessoas());
        dadosSala.setStatusSala(sala.isStatusSala());
        return salaRepository.save(dadosSala);
    }
}