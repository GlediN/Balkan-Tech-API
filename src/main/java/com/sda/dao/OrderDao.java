package com.sda.dao;

import com.sda.entities.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDao extends JpaRepository<Orders,Integer> {

}
