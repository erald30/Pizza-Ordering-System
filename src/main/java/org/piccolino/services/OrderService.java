package org.piccolino.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.piccolino.dto.OrderCreate;
import org.piccolino.dto.ProductQuantity;
import org.piccolino.entities.Order;
import org.piccolino.entities.OrderItem;
import org.piccolino.entities.Product;
import org.piccolino.repositories.OrderItemRepository;
import org.piccolino.repositories.OrderRepository;

import java.time.LocalDate;
import java.util.Optional;

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


    public void addProductQuantityToOrder(OrderCreate orderCreate, ProductQuantity productQuantity) {
        if (orderCreate == null)
            throw new RuntimeException("Order object is null");

        if (productQuantity == null)
            throw new RuntimeException("Product quantity object is null");

        Optional<ProductQuantity> existingProductQuantity =  orderCreate
                .getOrderItems()
                .stream()
                .filter(o -> o.getProductId() == productQuantity.getProductId())
                .findFirst();

        if (existingProductQuantity.isPresent()) {
            existingProductQuantity
                    .get()
                    .setQuantity(existingProductQuantity.get().getQuantity() + productQuantity.getQuantity());
        }
        else {
            orderCreate.getOrderItems().add(productQuantity);
        }

    }

    public Order getOrder(int id){
        return orderRepository.getById(id);
    }


    public void removeProductFromOrder(int idToRemove, OrderCreate order) {
        /*Optional<ProductQuantity> existingProductQuantity =  order
                .getOrderItems()
                .stream()
                .filter(o -> o.getProductId() == idToRemove)
                .findFirst();

        *//*if (existingProductQuantity.isPresent()) {
            order.getOrderItems().remove(existingProductQuantity.get());
        }*//*
        existingProductQuantity.ifPresent(productQuantity -> order.getOrderItems().remove(productQuantity));*/
        // Alternative
        order
                .getOrderItems()
                .stream()
                .filter(o -> o.getProductId() == idToRemove)
                .findFirst().ifPresent(productQuantity -> order.getOrderItems().remove(productQuantity));

    }

    public Order save(OrderCreate orderCreate) {
        if (orderCreate == null)
            throw new RuntimeException("Order object is null");

        if (orderCreate.getOrderItems().isEmpty())
            throw new RuntimeException("Order should contain at least 1 product!");

        Transaction tx = null;

        try {
            Session session = orderRepository.getSessionFactory().openSession();
            tx = session.beginTransaction();

            // 1. Create order object in database
            Order newOrder = Order.builder()
                    .orderDate(LocalDate.now())
                    .build();
            session.persist(newOrder);

            // 2. Iterate each ProductQuantity in order object
            // 3. Create OrderItem for each ProductQuantity
            // 4. Save OrderItem in database
            double total = 0;
            for (ProductQuantity item : orderCreate.getOrderItems()) {
                OrderItem newOrderItem = OrderItem.builder()
                        .orderId(newOrder.getId())
                        .productId(item.getProductId())
                        .quantity(item.getQuantity())
                        .total(item.getProductPrice() * item.getQuantity())
                        .build();

                session.persist(newOrderItem);
                total += newOrderItem.getTotal();
            }

            // 5. Update total on Order object and save to database
            newOrder.setTotalPrice(total);
            session.persist(newOrder);

            tx.commit();

            return newOrder;
        } catch (Exception ex) {
            ex.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }

            throw new RuntimeException("Error during save of Order");
        }
    }

    public void printInvoice(int orderId){
        Order order = orderRepository.getById(orderId);
        if (order == null) {
            System.out.println("*              |---------------------------------------------------------|              *");
            System.out.println(String.format("*                           Order with id %s is not found                               *", orderId));
            System.out.println("*              |---------------------------------------------------------|              *");

            return;
        }
        System.out.println("*              |---------------------------------------------------------|              *");
        System.out.println("*              |---------------------------------------------------------|              *");
        System.out.println(String.format("*              |              <-- Fatura Juaj: %s -->                    |              *", orderId));
        System.out.println("*              |------|--------------------------|--------|-----|--------|              *");
        System.out.println("*              | ID   | Title                    | Price  | Qty | SubTot |              *");
        System.out.println("*              |------|--------------------------|--------|-----|--------|              *");



        for (OrderItem orderItem : order.getOrderItems()) {
            System.out.printf("*              | %-5s| %-25s| %-7sX %-4s| %-7s|              *%n",
                    orderItem.getProduct().getId(),
                    orderItem.getProduct().getTitle(),
                    orderItem.getProduct().getPrice(),
                    orderItem.getQuantity(),
                    orderItem.getTotal());
        }

        System.out.println("*              |______|__________________________|________|_____|________|              *");
        System.out.printf("*              |                                  TOTAL     %-9s    |              *%n", order.getTotalPrice());
        System.out.println("*              |_________________________________________________________|              *");
        System.out.println("*                                                                                       *");
    }
}
