package com.rajasekhar.dreamshops.repository;

import com.rajasekhar.dreamshops.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<Order, Long> {
}
