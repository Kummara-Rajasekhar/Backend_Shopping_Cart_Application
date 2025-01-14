package com.rajasekhar.dreamshops.service.Cart;

import com.rajasekhar.dreamshops.exceptions.ResourceNotFoundException;
import com.rajasekhar.dreamshops.model.Cart;
import com.rajasekhar.dreamshops.repository.CartItemRepository;
import com.rajasekhar.dreamshops.repository.CartRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ICartservice implements CartService {
    private  final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public Cart getCart(Long id) {
        Cart cart = cartRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Cart not found!"));
        BigDecimal totalAmount=cart.getTotalAmout();
        cart.setTotalAmout(totalAmount);
        return cartRepository.save(cart);
    }


    @Transactional
    @Override
    public void clearCart(Long id) {
        Cart cart = getCart(id);
        CartItemRepository.deleteAllByCart(id);
        cart.getCartItems().clear();
        cartRepository.deleteByid(id);
    }

    @Override
    public BigDecimal getTotal(Long id) {

        Cart cart = getCart(id);

        return cart.getTotalAmout();
    }

    @Override
    public Long initailizerNewCart() {
        return 0L;
    }


    @Override
    public Optional<Cart> getCartByUserId(Long userId){
        return cartRepository.findById(userId);
    }
}
