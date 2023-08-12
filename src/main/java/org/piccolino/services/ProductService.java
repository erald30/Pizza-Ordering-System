package org.piccolino.services;

import lombok.RequiredArgsConstructor;
import org.piccolino.dto.OrderCreate;
import org.piccolino.dto.ProductQuantity;
import org.piccolino.entities.Product;
import org.piccolino.repositories.ProductRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public void createProductItems() {
        List<Product> products = buildProducts();
        for(Product product: products){
            Product dbProduct = productRepository.getByTitle(product.getTitle());
            if(dbProduct == null){
                productRepository.save(product);
            }
        }
    }

    public Product getProductById(int id){
        return productRepository.getById(id);
    }

    private List<Product> buildProducts(){
        List<Product> productsList = new ArrayList<>();
        productsList.add(Product.builder().title("Pizza Margarita").price(300).build());
        productsList.add(Product.builder().title("Pizza Vegjetariane").price(500).build());
        productsList.add(Product.builder().title("Pizza Diavola").price(600).build());
        productsList.add(Product.builder().title("Pizza Proshute Kerpudhe").price(500).build());
        productsList.add(Product.builder().title("Pizza Proshute Sallam").price(740).build());
        productsList.add(Product.builder().title("Pizza Vegjetariane").price(860).build());
        productsList.add(Product.builder().title("Pizza Deluxe").price(480).build());
        productsList.add(Product.builder().title("Pizza Supreme").price(670).build());
        productsList.add(Product.builder().title("Pizza 4 Stinet").price(850).build());
        productsList.add(Product.builder().title("Pizza La Crema").price(800).build());
        productsList.add(Product.builder().title("Pizza Burn").price(900).build());
        productsList.add(Product.builder().title("Pizza Barbeque").price(910).build());
        productsList.add(Product.builder().title("Pizza Suxhuk").price(930).build());
        productsList.add(Product.builder().title("Pizza Artixhano").price(950).build());
        productsList.add(Product.builder().title("Pizza Buffalo").price(1060).build());
        productsList.add(Product.builder().title("Pizza Ton").price(860).build());

        return productsList;
    }

    public void printProductsMenu(){
        List<Product> products = productRepository.getAllProducts();

        if(products == null){
            System.out.println("No Products In Menu");
            return;
        }

        System.out.println("*                    |--------------------------------------------|                     *");
        System.out.println("*                    |        <-- Menuja e Produkteve -->         |                     *");
        System.out.println("*                    |------|--------------------------|----------|                     *");
        System.out.println("*                    | ID   | Title                    | Price    |                     *");
        System.out.println("*                    |------|--------------------------|----------|                     *");

        for (Product product : products) {
            System.out.printf("*                    | %-5s| %-25s| %-9s|                     *%n",
                    product.getId(), product.getTitle(), product.getPrice());
        }

        System.out.println("*                    |______|__________________________|__________|                     *");
        System.out.println("*                                                                                       *");
    }

    public void printMainMenu(){
        System.out.println("*                    |--------------------------------------------|                     *");
        System.out.println("*                    |          <-- Menuja Kryesore -->           |                     *");
        System.out.println("*                    |--------------------------------------------|                     *");
        System.out.println("*                    |-----------|--------------------------------|                     *");
        System.out.println("*                    | OPTION    | ACTION                         |                     *");
        System.out.println("*                    |-----------|--------------------------------|                     *");
        System.out.println("*                    | 1         | Porosi                         |                     *");
        System.out.println("*                    | 2         | Kerko fature                   |                     *");
        System.out.println("*                    | 3         | Mbyll programin                |                     *");
        System.out.println("*                    |___________|________________________________|                     *");
        System.out.println("*                                                                                       *");
    }

    public void printWelcome(){
        System.out.println("*┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓*");
        System.out.println("*┃ *             *  * * * * *  *           * * * *   * * * *   * *     * *  * * * * *  ┃*");
        System.out.println("*┃  *     *     *   *          *          *         *       *  *  *   *  *  *          ┃*");
        System.out.println("*┃   *   * *   *    * * * *    *          *         *       *  *   * *   *  * * * *    ┃*");
        System.out.println("*┃    * *   * *     *          *          *         *       *  *    *    *  *          ┃*");
        System.out.println("*┃     *     *      * * * * *  * * * * *   * * * *   * * * *   *         *  * * * * *  ┃*");
        System.out.println("*┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛*");
        System.out.println("*                                                                                       *");

    }

    public void printOrderMenu(){
        System.out.println("*                    |-----------|--------------------------------|                     *");
        System.out.println("*                    | OPSIONI   | VEPRIMI                        |                     *");
        System.out.println("*                    |-----------|--------------------------------|                     *");
        System.out.println("*                    | 1         | Vazhdo Porosine                |                     *");
        System.out.println("*                    | 2         | Fshi Produkt                   |                     *");
        System.out.println("*                    | 3         | Perfundo Porosi                |                     *");
        System.out.println("*                    |___________|________________________________|                     *");
        System.out.println("*                                                                                       *");
    }

    public void showOrderItems(OrderCreate order){
        System.out.println("*                  |------------------------------------------------|                   *");
        System.out.println("*                  |       <-- Produktet ne porosine tuaj -->       |                   *");
        System.out.println("*                  |------|--------------------------|--------|-----|                   *");
        System.out.println("*                  | ID   | Title                    | Price  | Qty |                   *");
        System.out.println("*                  |------|--------------------------|--------|-----|                   *");

        for (ProductQuantity orderItem : order.getOrderItems()) {
            System.out.printf("*                  | %-5s| %-25s| %-7s| %-4s|                   *%n",
                    orderItem.getProductId(), orderItem.getProductTitle(), orderItem.getProductPrice(), orderItem.getQuantity());
        }

        System.out.println("*                  |______|__________________________|________|_____|                   *");
        System.out.println("*                                                                                       *");
    }
}
