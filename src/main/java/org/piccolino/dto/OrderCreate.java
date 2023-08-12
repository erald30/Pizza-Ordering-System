package org.piccolino.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderCreate {
    protected List<ProductQuantity> orderItems = new ArrayList<>();
}
