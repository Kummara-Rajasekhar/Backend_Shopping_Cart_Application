package com.rajasekhar.dreamshops.service.CartItem;

public interface ICartItemService {

    void addItemToCart(Long cartId, Long productId,int quantity) ;
    void removeItemFromCart(Long cartId, Long productId);
    void updateItemInCart(Long cartId, Long productId, int quantity);


}
