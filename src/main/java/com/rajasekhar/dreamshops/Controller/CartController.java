package com.rajasekhar.dreamshops.Controller;


import com.rajasekhar.dreamshops.exceptions.ResourceNotFoundException;
import com.rajasekhar.dreamshops.model.Cart;
import com.rajasekhar.dreamshops.response.ApiResponse;
import com.rajasekhar.dreamshops.service.Cart.ICartservice;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api/prefix}/cart")
public class CartController {
    private final ICartservice cartservice;

    @GetMapping("/{cartId}/my-cart")
    public ResponseEntity<ApiResponse> getCart(@PathVariable Long cartId){
        try{
            Cart cart =cartservice.getCart(cartId);
            return ResponseEntity.ok(new ApiResponse("Found!",cart));

        }
        catch (ResourceNotFoundException e){
            return ResponseEntity.status(404).body(new ApiResponse(e.getMessage(),null));
        }
    }


    @DeleteMapping("/{cartId}/clear")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable Long cartId){
        try{
            cartservice.clearCart(cartId);
            return ResponseEntity.ok(new ApiResponse("Cart cleared!",null));
        }
        catch (ResourceNotFoundException e){
            return ResponseEntity.status(404).body(new ApiResponse(e.getMessage(),null));
        }
    }


    @GetMapping("/{cartId}/cart/total-price")
    public ResponseEntity<ApiResponse> getTotalAmount(@PathVariable Long cartId){
        try{

            BigDecimal totalAmount=cartservice.getTotal(cartId);
            return ResponseEntity.ok(new ApiResponse("Found!",totalAmount));
        }
        catch (ResourceNotFoundException e){
            return ResponseEntity.status(404).body(new ApiResponse(e.getMessage(),null));
        }
    }




}
