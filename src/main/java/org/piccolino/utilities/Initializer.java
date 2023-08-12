package org.piccolino.utilities;

import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.piccolino.dto.OrderCreate;
import org.piccolino.dto.ProductQuantity;
import org.piccolino.entities.Order;
import org.piccolino.entities.OrderItem;
import org.piccolino.entities.Product;
import org.piccolino.repositories.OrderItemRepository;
import org.piccolino.repositories.OrderRepository;
import org.piccolino.repositories.ProductRepository;
import org.piccolino.services.OrderService;
import org.piccolino.services.ProductService;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

@RequiredArgsConstructor
public class Initializer {
    public static void init() {
        SessionFactory sessionFactory = DbConnection.getFACTORY();

        ProductRepository productRepository = new ProductRepository(sessionFactory);
        OrderRepository orderRepository = new OrderRepository(sessionFactory);
        OrderItemRepository orderItemRepository = new OrderItemRepository(sessionFactory);

        ProductService productService = new ProductService(productRepository);
        OrderService orderService = new OrderService(orderRepository, orderItemRepository);

        productService.createProductItems();
        System.out.println("*****************************************************************************************");
        productService.printWelcome();

//        System.out.println(productService.getProductById(1).getTitle());


        Scanner sc = new Scanner(System.in);
        while (true) {
            productService.printMainMenu();
            System.out.print("*         Zgjidhni veprimin qe doni te kryeni:");
            int choice = sc.nextInt();
            switch (choice) {
                case 3 -> {
                    System.out.println("*         Faleminderit qe na zgjodhet!                                                  *");
                    System.out.println("*****************************************************************************************");
                    return;
                }
                case 1 -> {
                    OrderCreate orderCreate = new OrderCreate();
                    while (true) {

                        productService.printProductsMenu();
                        System.out.print("*         Zgjidhni ID e produktit:");
                        int productId = sc.nextInt();
                        Product p;
                        if ((p = productService.getProductById(productId)) == null) {
                            System.out.println("*         Gabim ne sistem!                               *");
                            return;
                        }
                        System.out.print("*         Vendosni sasine per produktin e zgjedhur:");
                        int quantity = sc.nextInt();

                        ProductQuantity productQuantity = ProductQuantity.builder()
                                .quantity(quantity)
                                .productId(p.getId())
                                .productTitle(p.getTitle())
                                .productPrice(p.getPrice())
                                .build();
                        orderService.addProductQuantityToOrder(orderCreate, productQuantity);

                        System.out.println("*         Produkti u zgjodh me sukses.                                                  *");

                        productService.printOrderMenu();
                        System.out.print("*         Zgjidhni veprimin qe doni te kryeni me pas:");
                        int option = sc.nextInt();
                        if (option == 1) {
                            continue;
                        } else if (option == 2) {
                            productService.showOrderItems(orderCreate);
                            System.out.print("*         Vendosni id e produktit qe doni te fshini:");
                            int idToBeDeleted = sc.nextInt();
                            orderService.removeProductFromOrder(idToBeDeleted, orderCreate);
                            System.out.println("*         Produkti u fshi me sukses!                                                    *");
                        } else if (option == 3) {
                            Order newOrder = orderService.save(orderCreate);
                            //orderService.completeOrder(order);
                            //orderService.saveOrderItems(order);
                            System.out.println("*         Porosia juaj u perfundua me sukses.                                           *");
                            orderService.printInvoice(newOrder.getId());
                            break;
                        }
                    }
                }
                case 2 -> {
                    System.out.print("*         Vendosni ID e fatures qe po kerkoni:");
                    int orderId = sc.nextInt();
                    orderService.printInvoice(orderId);
                }
            }
        }
    }
}
