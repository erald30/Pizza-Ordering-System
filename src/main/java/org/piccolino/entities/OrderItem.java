package org.piccolino.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pc_order_item")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "product_id")
    private  int productId;

    @Column(name = "order_id")
    private int orderId;

    @ManyToOne
    @JoinColumn(name = "order_id", insertable = false, updatable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;

    private int quantity;

    private double total;
}
