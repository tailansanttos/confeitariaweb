package com.tailan.confeitaria.web.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nome;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String telefone;
    private String password;
    @Column(unique = true)
    private String cpf;

    @OneToMany
    private List<Endereco> enderecos;

    @OneToMany
    private List<Pedido> pedidos;
}
