package org.piccolino.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Entity
@Table(name = "pc_order")
@Getter @Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;

    @Column(name = "order_date")
    private LocalDate orderDate;

    @Column(name = "total_price")
    private double totalPrice;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    @PrePersist
    private void createdOn(){
        orderDate = LocalDate.now();
    }

    public void addOrderItem(Product product, int quantity) {
        OrderItem orderItem = OrderItem.builder()
                .order(this)
                .product(product)
                .quantity(quantity)
                .total(product.getPrice() * quantity)
                .build();
        orderItems.add(orderItem);
        this.totalPrice += product.getPrice() * quantity;
    }

    public void removeOrderItem(int id){
        OrderItem itemToRemove = null;
        for (OrderItem item : orderItems) {
            if (item.getProduct().getId() == id) {
                itemToRemove = item;
                break;
            }
        }

        if (itemToRemove != null) {
            orderItems.remove(itemToRemove);
            totalPrice -= itemToRemove.getProduct().getPrice() * itemToRemove.getQuantity();
        }
    }



}
