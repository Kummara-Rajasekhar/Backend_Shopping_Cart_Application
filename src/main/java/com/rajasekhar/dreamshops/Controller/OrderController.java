package com.rajasekhar.dreamshops.Controller;


import com.rajasekhar.dreamshops.dto.OrderDto;
import com.rajasekhar.dreamshops.exceptions.ResourceNotFoundException;
import com.rajasekhar.dreamshops.model.Order;
import com.rajasekhar.dreamshops.response.ApiResponse;
import com.rajasekhar.dreamshops.service.order.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final IOrderService orderService;


    @PostMapping("/order")
    public ResponseEntity<ApiResponse> createOrder(@PathVariable Long userId){
        try{
            Order order=orderService.placeOrder(userId);
            return ResponseEntity.ok(new ApiResponse("Order placed!",order));
        }
        catch (ResourceNotFoundException e){
            return ResponseEntity.status(404).body(new ApiResponse(e.getMessage(),null));
        }
    }


    @GetMapping("/{orderId}/orders")
    public  ResponseEntity<ApiResponse> getOrderById(@PathVariable Long orderId){
        try{
            Order order =orderService.getOrder(orderId);
            return ResponseEntity.ok(new ApiResponse("Found!",order));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(404).body(new ApiResponse(e.getMessage(),null));
        }
    }


    @GetMapping("/{userId}/orders")
    public  ResponseEntity<ApiResponse> getUserOrders(@PathVariable Long userId){
        try{
            List<Order> order = (List<Order>) orderService.getOrder(userId);
            return ResponseEntity.ok(new ApiResponse("Found!",order));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(404).body(new ApiResponse(e.getMessage(),null));
        }
    }

}
