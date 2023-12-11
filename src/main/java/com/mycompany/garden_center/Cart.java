/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.garden_center;

import java.util.HashMap;

/**
 *
 * @author Liana and Vinnie
 */
public class Cart {
    private final int cartId;
    private final int customerId;
    private CartStatus status;
    private final HashMap<Product, Integer> products;
    private double totalCost;

    public Cart(int cartId, int customerId, CartStatus status) {
        this.cartId = cartId;
        this.customerId = customerId;
        this.status = status;
        this.products = new HashMap<>();
        this.totalCost = 0.0;
    }

    public int getCartId() {
        return cartId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public CartStatus getStatus() {
        return status;
    }

    public HashMap<Product, Integer> getProducts() {
        return products;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void addProduct(Product product, int quantity) {
        products.put(product, quantity);
        // Update total cost
        updateTotalCost();
    }

    public void deleteProduct(Product product) {
        products.remove(product);
        // Update total cost
        updateTotalCost();
    }

    public void editQuantity(Product product, int newQuantity) {
        products.put(product, newQuantity);
        // Update total cost
        updateTotalCost();
    }

    public void updateStatus(CartStatus newStatus) {
        status = newStatus;
    }

    public void updateTotalCost() {
        double cost = 0.0;
        for (Product product : products.keySet()) {
            int quantity = products.get(product);
            cost += product.getPrice() * quantity;
        }
        totalCost = cost;
    }

    public enum CartStatus {
        OPEN,
        CLOSED
    }
}
