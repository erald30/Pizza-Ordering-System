package org.piccolino.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Order_Item")
@Getter @Setter
public class OrderItem {

    private int id;

    @Column(name = "product_id")
    private  int productID;
    @Column(name = "order_id")
    private int orderID;
    private  int quantity;


}
