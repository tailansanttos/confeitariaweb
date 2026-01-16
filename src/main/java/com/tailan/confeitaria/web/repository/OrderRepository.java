package com.tailan.confeitaria.web.repository;

import com.tailan.confeitaria.web.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findAllByClient_Email(String clientEmail, Pageable pageable);

    Order findByClient_EmailAndId(String clientEmail, Long id);

    List<Order> findAllByStatusAndMomentOrderBefore(Integer status, Instant momentOrderBefore);
}
