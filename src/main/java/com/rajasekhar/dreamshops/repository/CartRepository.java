package com.rajasekhar.dreamshops.repository;

import com.rajasekhar.dreamshops.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository <Cart,Long>{

    void deleteByid(Long id);
}
