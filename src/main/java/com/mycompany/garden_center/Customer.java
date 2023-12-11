/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.garden_center;

// import necessary libraries
import java.util.Date;
import java.util.Map;
import java.util.ArrayList;

/**
 *
 * @author Liana and Vinnie
 */
public class Customer extends User {
    private String customerName;
    private String address;
    private String phoneNumber;
    private String creditCardNumber;
    private String shippingInfo;
    private final ArrayList<Cart> carts;
    
    public Customer(Integer id, String userName, String password, String customerName, String address, String phoneNumber, String creditCardNumber, String shippingInfo, String email, Date created_at) {
        super(id, userName, password, email, created_at);
        this.customerName = customerName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.creditCardNumber = creditCardNumber;
        this.shippingInfo = shippingInfo;
        this.carts = new ArrayList<>();
        // Create a new cart for the customer on creation
        this.carts.add(new Cart(1, this.getId(), Cart.CartStatus.OPEN));
    }

    // Login method
    public void login(String username, String password) {
        if (super.verifyLogin(username, password)) {
            // Pseudocode for logging the customer into the system
            System.out.println("Customer " + username + " is logged in.");
        }
    }

    // Getters
    public String getCustomerName() {
        return customerName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public String getShippingInfo() {
        return shippingInfo;
    }
    
    public ArrayList<Cart> getCarts() {
        return this.carts;
    }

    // Update customer profile
    public void updateProfile(Map<String, Object> attributes) {
        for (Map.Entry<String, Object> entry : attributes.entrySet()) {
            switch (entry.getKey()) {
                case "customerName":
                    this.customerName = (String) entry.getValue();
                    break;
                case "address":
                    this.address = (String) entry.getValue();
                    break;
                case "phoneNumber":
                    this.phoneNumber = (String) entry.getValue();
                    break;
                case "creditCardNumber":
                    this.creditCardNumber = (String) entry.getValue();
                    break;
                case "shippingInfo":
                    this.shippingInfo = (String) entry.getValue();
                    break;
                default:
                    throw new IllegalArgumentException("Invalid attribute: " + entry.getKey());
            }
        }
    }

    // Get cart from carts with cartId
    public Cart getCart(int cartId) {
        for (Cart cart : this.carts) {
            if (cart.getCartId() == cartId) {
                return cart;
            }
        }
        // Return error message if cart not found
        throw new IllegalArgumentException("Cart with id " + cartId + " not found.");
    }

    // Get current cart (cart with status OPEN) if no cart is found, create a new cart and return the new cart
    public Cart getCurrentCart() {
        // Try to find an open cart
        for (Cart cart : this.carts) {
            if (cart.getStatus() == Cart.CartStatus.OPEN) {
                return cart;
            }
        }
    
        // If no open cart is found, create a new one
        Cart newCart = this.createNewCart();
        return newCart;
    }

    // Create a new cart for the customer
    private Cart createNewCart() {
        int newCartId = (this.carts.isEmpty()) ? 1 : this.carts.get(this.carts.size() - 1).getCartId() + 1;
        Cart newCart = new Cart(newCartId, this.getId(), Cart.CartStatus.OPEN);
        this.carts.add(newCart);
        return newCart;
    }


    // Create a new order from the current cart
    public void createOrder() {
        // Create a new order from the current cart
        Cart currentCart = this.getCurrentCart();
        ArrayList<OrderLineItem> lineItems = new ArrayList<>();
        float totalAmount = 0;
        
        // Create a new order with orderId = last order's orderId + 1
        int newOrderId = (this.getOrders().isEmpty()) ? 1 : this.getOrders().get(this.getOrders().size() - 1).getOrderId() + 1;
        Order newOrder = new Order(newOrderId, this.getId(), currentCart.getCartId(), Order.Status.OPEN, new Date());
        this.getOrders().add(newOrder);
        
        // Create line items for the order
        int lineItemCount = 0;
        for (Map.Entry<Product, Integer> entry : currentCart.getProducts().entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            lineItemCount += quantity;
            OrderLineItem lineItem = new OrderLineItem(lineItemCount, product.getProductId(), newOrder.getOrderId());
            lineItems.add(lineItem);
            totalAmount += product.getPrice() * quantity; // Assumes Product has a getPrice() method
        }

        // Update the order's line items and total amount
        newOrder.setLineItems(lineItems);
        newOrder.setTotalAmount(totalAmount);

        // Update the current cart's status to CLOSED
        currentCart.updateStatus(Cart.CartStatus.CLOSED);

        // Create a new cart for the customer
        this.createNewCart();
    }

    // Pay for an order
    public void makePayment(int orderId, String shippingInfo, String creditCardNumber) {
        Order orderToPay = null;
        
        // Find the order to pay
        for (Order order : this.getOrders()) {
            if (order.getOrderId() == orderId) {
                orderToPay = order;
                break;
            }
        }
        // Return error message if order not found
        if (orderToPay == null) {
            throw new IllegalArgumentException("Order not found.");
        }
    
        // Get order total amount
        float totalAmount = orderToPay.getTotalAmount();
    
        // Simulate payment process
        boolean paymentSuccessful = processPayment(totalAmount);
    
        if (paymentSuccessful) {
            // Output the payment message
            System.out.println("Order of total amount " + totalAmount + " was successfully paid with credit card " + this.creditCardNumber + " and it'll be shipped to " + shippingInfo + ".");
            // Update the order's status to PAID
            orderToPay.updateStatus(Order.Status.PAID);
        } else {
            System.out.println("Payment unsuccessful.");
            orderToPay.updateStatus(Order.Status.OPEN);
        }
    }
    
    private boolean processPayment(float amount) {
        // Simulate payment processing. In a real application, this method would interact with a payment gateway.
        // For now, we'll simulate a payment failure if the amount is greater than 1000.
        return amount <= 1000;
    }
}
