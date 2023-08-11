package org.piccolino;

import org.hibernate.SessionFactory;
import org.piccolino.repositories.OrderItemRepository;
import org.piccolino.repositories.OrderRepository;
import org.piccolino.repositories.ProductRepository;
import org.piccolino.services.ProductService;
import org.piccolino.utilities.DbConnection;
import org.piccolino.utilities.Initializer;


public class Main {
    public static void main(String[] args) {
        Initializer.init();
    }
}
