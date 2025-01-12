package com.rajasekhar.dreamshops.repository;

import com.rajasekhar.dreamshops.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    static void deleteAllByCart(Long id) {
    }
}
