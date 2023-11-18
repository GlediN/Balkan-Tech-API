package com.sda.repositories;

import com.sda.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemsDAO extends JpaRepository<OrderItem,Integer> {
}
