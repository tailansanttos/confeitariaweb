package com.tailan.confeitaria.web.repository;

import com.tailan.confeitaria.web.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findCartByUser_Email(String userEmail);
}
