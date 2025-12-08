package com.tailan.confeitaria.web.domain;

import jakarta.persistence.*;

import java.util.Set;
import java.util.UUID;

@Table(name = "tb_categorias")
@Entity
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nome;

    @OneToMany(mappedBy = "categoria")
    private Set<Produto> produtos;



    public Categoria(String nome, Set<Produto> produtos) {
        this.nome = nome;
        this.produtos = produtos;
    }

    public Categoria() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
