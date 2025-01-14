package com.rajasekhar.dreamshops.service.Cart;

import com.rajasekhar.dreamshops.model.Cart;

import java.math.BigDecimal;
import java.util.Optional;

public interface CartService {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotal(Long id);


    Long initailizerNewCart();

    Optional<Cart> getCartByUserId(Long userId);
}
