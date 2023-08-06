package org.piccolino;

import org.hibernate.SessionFactory;
import org.piccolino.entities.Product;
import org.piccolino.repositories.OrderRepository;
import org.piccolino.repositories.ProductRepository;
import org.piccolino.utilities.DbConnection;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        SessionFactory sessionFactory = DbConnection.getFACTORY();

        ProductRepository productRepository = new ProductRepository(sessionFactory);
        OrderRepository orderRepository = new OrderRepository(sessionFactory);
    }
}
