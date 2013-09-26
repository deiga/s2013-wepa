package wad.ordering.service;

import java.util.List;
import wad.ordering.domain.Order;

public interface OrderService {

    Order placeOrder(Order order);
    Order getOrder(String id);
    List<Order> getOrders();
}
