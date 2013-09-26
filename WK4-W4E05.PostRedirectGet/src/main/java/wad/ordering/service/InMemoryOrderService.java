package wad.ordering.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Service;
import wad.ordering.domain.Order;

@Service
public class InMemoryOrderService implements OrderService {

    private Map<String, Order> orders;

    public InMemoryOrderService() {
        this.orders = new HashMap<String, Order>();
    }

    @Override
    public Order placeOrder(Order order) {
        order.setId(UUID.randomUUID().toString());
        this.orders.put(order.getId(), order);
        
        return order;
    }

    @Override
    public Order getOrder(String id) {
        if (!orders.containsKey(id)) {
            throw new IllegalArgumentException("No such order: " + id);
        }

        return orders.get(id);
    }

    @Override
    public List<Order> getOrders() {
        return new ArrayList(orders.values());
    }
}
