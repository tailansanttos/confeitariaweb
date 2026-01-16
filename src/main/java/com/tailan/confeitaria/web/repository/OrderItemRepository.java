package com.tailan.confeitaria.web.repository;

import com.tailan.confeitaria.web.domain.OrderItem;
import com.tailan.confeitaria.web.domain.OrderItemPk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPk> {

}
