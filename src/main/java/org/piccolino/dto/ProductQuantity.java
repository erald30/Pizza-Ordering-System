package org.piccolino.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductQuantity {
    protected int productId;
    protected String productTitle;
    protected double productPrice;
    protected int quantity;
}
