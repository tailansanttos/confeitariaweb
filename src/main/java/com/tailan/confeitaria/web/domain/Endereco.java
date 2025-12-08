package com.tailan.confeitaria.web.domain;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "tb_enderecos")
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID   id;
    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String numero;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Usuario usuario;
}
