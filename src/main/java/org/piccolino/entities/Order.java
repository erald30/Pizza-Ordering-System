package org.piccolino.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "pc_order")
@Getter @Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private  int id;

    @Column(name = "order_date")
    private Date orderDate;

    @Column(name = "total_date")
    private double totalPrice;

}
