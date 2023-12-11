/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.garden_center;

/**
 *
 * @author Liana and Vinnie
 */
public class OrderLineItem {
    private final int lineItemId;
    private final int orderId;
    private final int productId;

    public OrderLineItem(int lineItemId, int orderId, int productId) {
        this.lineItemId = lineItemId;
        this.orderId = orderId;
        this.productId = productId;
    }

    public int getLineItemId() {
        return lineItemId;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getProductId() {
        return productId;
    }
}
