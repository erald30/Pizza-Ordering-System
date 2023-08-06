package org.piccolino.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Product")
@Getter @Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private  int id;

    @Column(name = "product_name")
    private String productName;

    private double price;


    private List<Product> productList;







}
