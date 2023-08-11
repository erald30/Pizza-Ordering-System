package org.piccolino.services;

import lombok.RequiredArgsConstructor;
import org.piccolino.entities.Order;
import org.piccolino.entities.OrderItem;
import org.piccolino.entities.Product;
import org.piccolino.repositories.OrderItemRepository;
import org.piccolino.repositories.OrderRepository;

@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public void completeOrder(Order order){
        orderRepository.save(order);
    }

    public void saveOrderItems(Order order){
        for (OrderItem item : order.getOrderItems()){
            orderItemRepository.save(item);
        }
    }

    public Order getOrder(int id){
        return orderRepository.getById(id);
    }


}
