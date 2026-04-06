package br.com.alura.provapratica.model;


import jakarta.annotation.Generated;
import jakarta.persistence.*;
import org.hibernate.boot.registry.selector.spi.StrategyCreator;

@Entity
public class Sala {

    @Id @GeneratedValue(strategy =GenerationType.AUTO)
    private Long id;
    @Column(name = "nome_da_sala", nullable = false, unique = true)
    private int numeroSala;
    @Column(name = "quantidade_de_Pessoas_permitidas", nullable = false)
    private int quantidadePessoas;
    @Column(name = "status_da_sala", nullable = false)
    private boolean statusSala;


    public Sala(int numeroSala, int quantidadePessoas, boolean statusSala, Long id) {
        if (quantidadePessoas>0){
            this.numeroSala = numeroSala;
            this.quantidadePessoas = quantidadePessoas;
            this.statusSala = statusSala;
            this.id = id;
        }else{
            throw new RuntimeException("A Sala tem que ter capacidade maior que 0");
        }

    }

    public Sala() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNumeroSala(int numeroSala) {
        this.numeroSala = numeroSala;
    }

    public void setStatusSala(boolean statusSala) {
        this.statusSala = statusSala;
    }

    public void setQuantidadePessoas(int quantidadePessoas) {
        this.quantidadePessoas = quantidadePessoas;
    }

    public boolean isStatusSala() {
        return statusSala;
    }

    public int getQuantidadePessoas() {
        return quantidadePessoas;
    }

    public int getNumeroSala() {
        return numeroSala;
    }
}
