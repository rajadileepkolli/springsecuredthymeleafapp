package com.learning.securedapp.web.domain;

import com.learning.securedapp.domain.Product;
import java.io.Serializable;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

/**
 * OrderLine class.
 *
 * @author rajakolli
 * @version $Id: $Id
 */
@Data
@Builder
public class OrderLine implements Serializable {

    private static final long serialVersionUID = 1L;

    private Product goods;
    private int quantity;
    private UUID orderId;
    private int lineNo;

    /**
     * getSubtotal.
     *
     * @return a double.
     */
    public double getSubtotal() {
        return quantity * goods.getPrice();
    }
}
