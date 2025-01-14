package com.rajasekhar.dreamshops.service.order;

import com.rajasekhar.dreamshops.enums.OrderStatus;
import com.rajasekhar.dreamshops.model.Cart;
import com.rajasekhar.dreamshops.model.Order;
import com.rajasekhar.dreamshops.model.OrderItem;
import com.rajasekhar.dreamshops.model.Product;
import com.rajasekhar.dreamshops.repository.OrderRepository;
import com.rajasekhar.dreamshops.repository.ProductRepository;
import com.rajasekhar.dreamshops.service.Cart.CartService;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;


@Service
@RequiredArgsConstructor
public  class orderService implements IOrderService {


    private  final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartService cartService;


    @Override
    public Order placeOrder(Long userId) {
        Cart cart =cartService.getCart(userId);
        Order order=createOrder(cart);
        List<OrderItem> orderItems=createOrderItems(order,cart);
        order.setOrderItems(new HashSet<>(orderItems));
        order.setTotalAmount(calculateTotalAmount(orderItems));
        Order dbOrder=orderRepository.save(order);
        cartService.clearCart(cart.getId());


        return dbOrder;
    }

    @Override
    public Order getOrder(Long orderId) {
        return null;
    }


    private Order createOrder(Cart cart){
        Order order=new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        Order.setOrderDate(LocalDate.now());
        return order;
    }


    private List<OrderItem> createOrderItems(Order order, Cart cart){
        return cart.getCartItems().stream()
                .map(item-> {
                    Product prduct = item.getProduct();
                    prduct.setInventory(prduct.getInventory() - item.getQuantity());
                    productRepository.save(prduct);
                    return new OrderItem(
                            order,
                            prduct,
                            item.getQuantity(),
                            item.getUnitPrice()
                    );
                }).toList();

        }
    }


    private static BigDecimal calculateTotalAmount(List<OrderItem> orderItemList){
        return orderItemList
                .stream()
                .map(item->item.getPrice()
                        .multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO,BigDecimal::add);
    }


    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(()->new RuntimeException("Order not found"));
    }


    public List<Order> getUserOrdrs(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}
