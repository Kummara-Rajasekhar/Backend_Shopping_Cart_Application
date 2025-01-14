package com.rajasekhar.dreamshops.Controller;

import com.rajasekhar.dreamshops.exceptions.ResourceNotFoundException;
import com.rajasekhar.dreamshops.response.ApiResponse;
import com.rajasekhar.dreamshops.service.Cart.CartService;
import com.rajasekhar.dreamshops.service.CartItem.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart-items")
@RequiredArgsConstructor
public class CartItemController {


    private final CartItemService cartItemService;
    private final CartService cartService;


    @PostMapping("/items/add")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestParam Long cartId,
                                                     @RequestParam Long productId,
                                                     @RequestParam Integer quantity){

        try{
            if(cartId==null){
                cartId=cartService.initailizerNewCart();
            }
            cartItemService.addItemToCart(cartId,productId,quantity);
            return ResponseEntity.ok(new ApiResponse("Item added to cart!",null));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(404).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping("/{cartId}/Item/{ItemId}/remove")
    public ResponseEntity<ApiResponse> removeItemFromCart(@RequestParam Long cartId,
                                                          @RequestParam Long productId){
        try{
            cartItemService.removeItemFromCart(cartId,productId);
            return ResponseEntity.ok(new ApiResponse("Item removed from cart!",null));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(404).body(new ApiResponse(e.getMessage(),null));
        }
    }


    @PutMapping("/cart/{cartId}/Item/{itemId}/update")
    public ResponseEntity<ApiResponse> updateItemQuantity(@PathVariable Long cartId,
                                                          @PathVariable Long itemId,
                                                          @RequestParam Integer quantity){
        try{
            cartItemService.updateItemInCart(cartId,itemId,quantity);
            return ResponseEntity.ok(new ApiResponse("Item quantity updated!",null));
        }
        catch (ResourceNotFoundException e){
            return ResponseEntity.status(404).body(new ApiResponse(e.getMessage(),null));
        }
    }

}
