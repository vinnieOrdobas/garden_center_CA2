/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import com.mycompany.garden_center.Cart;
import com.mycompany.garden_center.Customer;
import com.mycompany.garden_center.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 
 *
 * @author vinnie
 */
public class CustomerTest {
    private Customer customer;
    private Cart cart1;
    private Cart cart2;

    @BeforeEach
    public void setUp() {
        // Create a new customer object for each test
        customer = new Customer(1, "username", "password", "John Doe", "123 Main St", "1234567890", "1234567890123456", "Shipping Info", "john.doe@example.com", new Date());
        cart1 = new Cart(1, customer.getId(), Cart.CartStatus.OPEN);
        cart2 = new Cart(2, customer.getId(), Cart.CartStatus.CLOSED);
        customer.getCarts().add(cart1);
        customer.getCarts().add(cart2);
    }

    @Test
    public void testGetCustomerName() {
        assertEquals("John Doe", customer.getCustomerName());
    }

    @Test
    public void testGetAddress() {
        assertEquals("123 Main St", customer.getAddress());
    }

    @Test
    public void testGetPhoneNumber() {
        assertEquals("1234567890", customer.getPhoneNumber());
    }

    @Test
    public void testGetCreditCardNumber() {
        assertEquals("1234567890123456", customer.getCreditCardNumber());
    }

    @Test
    public void testGetShippingInfo() {
        assertEquals("Shipping Info", customer.getShippingInfo());
    }

    @Test
    public void testUpdateProfile() {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("customerName", "Jane Doe");
        attributes.put("address", "456 Elm St");
        attributes.put("phoneNumber", "9876543210");
        attributes.put("creditCardNumber", "6543210987654321");
        attributes.put("shippingInfo", "New Shipping Info");

        customer.updateProfile(attributes);

        assertEquals("Jane Doe", customer.getCustomerName());
        assertEquals("456 Elm St", customer.getAddress());
        assertEquals("9876543210", customer.getPhoneNumber());
        assertEquals("6543210987654321", customer.getCreditCardNumber());
        assertEquals("New Shipping Info", customer.getShippingInfo());
    }

    @Test
    public void testGetCart() {
        Cart actualCart = customer.getCart(1);
        assertEquals(cart1.getCartId(), actualCart.getCartId());
        assertEquals(cart1.getCustomerId(), actualCart.getCustomerId());
        assertEquals(cart1.getStatus(), actualCart.getStatus());
    }

    @Test
    public void testGetCurrentCart() {
        Cart actualCart = customer.getCurrentCart();
        assertEquals(cart1.getCartId(), actualCart.getCartId());
        assertEquals(cart1.getCustomerId(), actualCart.getCustomerId());
        assertEquals(cart1.getStatus(), actualCart.getStatus());
    }

    @Test
    public void testCreateOrder() {
        assertEquals(0, customer.getOrders().size());
        customer.createOrder();
        assertEquals(1, customer.getOrders().size());

        Order actualOrder = customer.getOrders().get(0);
        assertEquals(cart1.getCartId(), actualOrder.getCartId());
        assertEquals(cart1.getCustomerId(), actualOrder.getCustomerId());
        assertEquals(Order.Status.OPEN, actualOrder.getStatus());
    }

    @Test
    public void testMakePayment() {
        customer.createOrder();
        Order order = customer.getOrders().get(0);
        customer.makePayment(order.getOrderId(), "shippingInfo", "creditCardNumber");
        assertEquals(Order.Status.PAID, order.getStatus());

        // Simulate a large order that will cause the payment to fail
        customer.createOrder();
        Order largeOrder = customer.getOrders().get(1);
        largeOrder.setTotalAmount(2000);
        customer.makePayment(largeOrder.getOrderId(), "shippingInfo", "creditCardNumber");
        assertEquals(Order.Status.OPEN, largeOrder.getStatus());
    }
}
