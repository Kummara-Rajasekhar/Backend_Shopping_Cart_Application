package com.rajasekhar.dreamshops.service.CartItem;


import com.rajasekhar.dreamshops.model.Cart;
import com.rajasekhar.dreamshops.model.CartItem;
import com.rajasekhar.dreamshops.model.Product;
import com.rajasekhar.dreamshops.repository.CartItemRepository;
import com.rajasekhar.dreamshops.repository.CartRepository;
import com.rajasekhar.dreamshops.service.Cart.ICartservice;
import com.rajasekhar.dreamshops.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService{
    private final CartItemRepository cartItemRepository;
    private final IProductService productService;
    private final ICartservice cartservice;
    private final CartRepository cartRepository;

    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        Cart cart=cartservice.getCart(productId);
        Product product= productService.getProductById(productId);
        CartItem cartItem=cart.getCartItems()
                .stream()
                .filter(item-> {
                    return Objects.equals(item.getProduct().getId(), productId);
                })
                .findFirst().orElse(new CartItem());
        if(cartItem.getProduct()==null){
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
        }else{
            cartItem.setQuantity(cartItem.getQuantity()+quantity);

        }
        cartItem.setTotalPrice();
        cart.addItem(cartItem);
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);

    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
        Cart cart=cartservice.getCart(productId);
        CartItem itemToRemove=cart.getCartItems()
                .stream()
                .filter(item-> {
                    return Objects.equals(item.getProduct().getId(), productId);
                })
                .findFirst().orElseThrow(()->new RuntimeException("Product not found"));
        cart.removeItem(itemToRemove);
        cartItemRepository.delete(itemToRemove);
        cartRepository.save(cart);
    }

    @Override
    public void updateItemInCart(Long cartId, Long productId, int quantity) {
        Cart cart=cartservice.getCart(productId);
        CartItem itemToUpdate=cart.getCartItems()
                .stream()
                .filter(item-> {
                    return Objects.equals(item.getProduct().getId(), productId);
                })
                .findFirst().orElseThrow(()->new RuntimeException("Product not found"));
        itemToUpdate.setQuantity(quantity);
        itemToUpdate.setTotalPrice();
        BigDecimal totalAmount=cart.getTotalAmout();
        cart.setTotalAmout(totalAmount);
        cartRepository.save(cart);


    }
}
