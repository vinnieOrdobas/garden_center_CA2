package com.mycompany.garden_center;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.util.Date;
import java.util.ArrayList;

/**
 *
 * @author Liana and Vinnie
 */
public class Order {
    private final int orderId;
    private final int customerId;
    private final int cartId;
    private Status status;
    private final Date orderDate;
    private float totalAmount;
    private ArrayList<OrderLineItem> lineItems;

    public Order(int orderId, int customerId, int cartId, Status status, Date orderDate) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.cartId = cartId;
        this.status = status;
        this.orderDate = orderDate;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getCartId() {
        return cartId;
    }

    public Status getStatus() {
        return status;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    // Set totalAmount
    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    // Set lineItems
    public void setLineItems(ArrayList<OrderLineItem> lineItems) {
        this.lineItems = lineItems;
    }

    public ArrayList<OrderLineItem> getLineItems() {
        return this.lineItems;
    }

    public int getLineItemCount() {
        return this.lineItems.size();
    }

    public void updateStatus(Status newStatus) {
        this.status = newStatus;
    }

    public enum Status {
        OPEN,
        PAID,
        CANCELLED,
        SHIPPED,
        DELIVERED
    }
}
