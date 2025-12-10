package com.tailan.confeitaria.web.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "tb_produtos")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    @Column(name = "img_url")
    private String imgUrl;
    private Boolean  ativo;

    @ManyToOne
    @JoinColumn(name = "categoria", nullable = false)
    private Categoria categoriaProduto;

    public Produto(Integer id, String nome, String descricao, BigDecimal preco, String imgUrl, Boolean ativo, Categoria categoriaProduto) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.imgUrl = imgUrl;
        this.ativo = ativo;
        this.categoriaProduto = categoriaProduto;
    }
    public  Produto(){

    }
    public Produto(String nome, String descricao, BigDecimal preco, String imgUrl, Boolean ativo, Categoria categoriaProduto) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.imgUrl = imgUrl;
        this.ativo = ativo;
        this.categoriaProduto = categoriaProduto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Categoria getCategoriaProduto() {
        return categoriaProduto;
    }

    public void setCategoriaProduto(Categoria categoriaProduto) {
        this.categoriaProduto = categoriaProduto;
    }
}
