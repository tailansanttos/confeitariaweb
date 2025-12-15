package com.tailan.confeitaria.web.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tb_users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String phone;
    private String password;
    @Column(unique = true)
    private String cpf;

    @OneToMany(mappedBy = "client")
    private List<Address> addresses;


    @OneToMany(mappedBy = "client")
    private List<Order> orders;

    public User(List<Order> orders, List<Address> addresses, String cpf, String password, String phone, String email, String name, Long id) {
        this.orders = orders;
        this.addresses = addresses;
        this.cpf = cpf;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.name = name;
        this.id = id;
    }


    public User() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public List<Address> getEnderecos() {
        return addresses;
    }

    public void setEnderecos(List<Address> addresses) {
        this.addresses = addresses;
    }

    public List<Order> getPedidos() {
        return orders;
    }

    public void setPedidos(List<Order> orders) {
        this.orders = orders;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
