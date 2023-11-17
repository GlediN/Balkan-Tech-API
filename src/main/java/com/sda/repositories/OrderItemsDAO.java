package com.sda.dao;

import com.sda.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemsDAO extends JpaRepository<OrderItem,Integer> {
}
