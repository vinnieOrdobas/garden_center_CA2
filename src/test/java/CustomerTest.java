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
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * 
 *
 * @author Liana and Vinnie
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
        attributes.put("customerName", "Liana Gabor");
        attributes.put("address", "Fairview Road");
        attributes.put("phoneNumber", "02152552");
        attributes.put("creditCardNumber", "42456 4562 452");
        attributes.put("shippingInfo", "Fairview Road");

        // when
        customer.updateProfile(attributes);
        
        //then
        assertEquals("Liana Gabor",customer.getCustomerName());
        assertEquals("Fairview Road", customer.getAddress());
        assertEquals("02152552", customer.getPhoneNumber());
        assertEquals("42456 4562 452", customer.getCreditCardNumber());
        assertEquals("Fairview Road", customer.getShippingInfo());

    }
    
    @Test
    public void testReturnCart_IllegalArgumentException() {
        // Given setUp
        // Assume that necessary setup is done in the setUp method

        // When and Then
        assertThrows(IllegalArgumentException.class, () -> {
            customer.getCart(3);
        });
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
