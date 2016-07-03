package com.learning.securedapp.web.domain;

import java.io.Serializable;
import java.util.UUID;

import com.learning.securedapp.domain.Product;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderLine implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Product goods;
    private int quantity;
    private UUID orderId;
    private int lineNo;

    public double getSubtotal() {
        return quantity * goods.getPrice();
    }
}
