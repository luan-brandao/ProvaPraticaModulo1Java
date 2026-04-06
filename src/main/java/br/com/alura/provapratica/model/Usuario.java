package br.com.alura.provapratica.model;


import jakarta.persistence.*;

@Entity
public class Usuario {
    @Id @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "nome_pessoa" , nullable = false)
    private String nome;
    @Column (name = "email",nullable = false , unique = true)
    private String email;
    @Column(name = "telefone", unique = true)
    private String numero;

    public Usuario(Long id, String nome, String email, String numero) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.numero = numero;
    }

    public Usuario (){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String cpf) {
        this.email= cpf;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}
