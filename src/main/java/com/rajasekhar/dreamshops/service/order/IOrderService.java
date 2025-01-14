package com.rajasekhar.dreamshops.service.order;

import com.rajasekhar.dreamshops.model.Order;

public interface IOrderService {

    Order placeOrder(Long userId);
    Order getOrder(Long orderId);
}
