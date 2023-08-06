package org.piccolino.services;

import lombok.RequiredArgsConstructor;
import org.piccolino.entities.Product;
import org.piccolino.repositories.ProductRepository;

@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;


    public void createProductItems() {
        Product margarita = Product.builder()
                .title("Pizza Margarita")
                .price(300)
                .build();

        Product dbMargarita = productRepository.getByTitle(margarita.getTitle());
        if (dbMargarita == null)
            productRepository.save(margarita);
    }
}
