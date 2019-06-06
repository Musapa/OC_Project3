package com.openclassrooms.shopmanager.order;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.openclassrooms.shopmanager.product.Product;
import com.openclassrooms.shopmanager.product.ProductService;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

	@InjectMocks
	private OrderService orderService;

	@Mock
	private OrderRepository orderRepository;

	@Mock
	private ProductService productService;
	
	@Test
	public void addToCart() {
		
		Product product = new Product();
		product.setId(1L);

		when(productService.getByProductId(1L)).thenReturn(product);
		boolean result = orderService.addToCart(product.getId());

		assertEquals(true, result);
	}
	
	@Test
	public void saveOrder() {
		
		Order order = new Order();
        order.setId(1L);
        
        // seting argumnetCapture
        ArgumentCaptor<Order> arg = ArgumentCaptor.forClass(Order.class);   
    	orderService.saveOrder(order);
    	// verify save is called once and capturing argument
    	verify(orderRepository, times(1)).save(arg.capture());
    	// check if argumentCaptor was the order we created above
		assertEquals(order, arg.getValue());
	}
		
	@Test
	public void removeFromCart() {
		
		Product product = new Product();
		product.setId(1L);

		when(productService.getByProductId(1L)).thenReturn(product);
		orderService.addToCart(product.getId());

		orderService.removeFromCart(product.getId());
		
		Cart cart = orderService.getCart();
		assertEquals(0, cart.getCartLineList().size());

	}
	
	@Test
	public void isCartEmpty() {
		assertEquals(true, orderService.isCartEmpty());
	}
	
	@Test
	public void createOrder() {
			
		Order order = new Order();
		// seting argumnetCapture
        ArgumentCaptor<Order> arg = ArgumentCaptor.forClass(Order.class);   
    	orderService.createOrder(order);
    	// verify save is called once and capturing argument
    	verify(orderRepository, times(1)).save(arg.capture());
    	// check if argumentCaptor was the order we created above
		assertEquals(order, arg.getValue());
	}

}
