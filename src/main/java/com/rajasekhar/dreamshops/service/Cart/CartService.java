package com.rajasekhar.dreamshops.service.Cart;

import com.rajasekhar.dreamshops.model.Cart;

import java.math.BigDecimal;

public interface CartService {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotal(Long id);




}
