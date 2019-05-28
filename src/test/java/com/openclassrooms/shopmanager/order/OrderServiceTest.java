package com.openclassrooms.shopmanager.order;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.openclassrooms.shopmanager.product.ProductService;

public class OrderServiceTest {

	@InjectMocks
	private OrderService orderService;

	@Mock
	private OrderRepository orderRepository;

	@Mock
	private ProductService productService;
	
	@Test
	public void OrderService() {
		fail("Not yet implemented");
	}
	
	@Test
	public void addToCart() {
		fail("Not yet implemented");
	}
	
	@Test
	public void saveOrder() {
		fail("Not yet implemented");
	}
	
	@Test
	public void getCart() {
		fail("Not yet implemented");
	}
			
	@Test
	public void removeFromCart() {
		fail("Not yet implemented");
	}
	
	@Test
	public void isCartEmpty() {
		fail("Not yet implemented");
	}
	
	@Test
	public void createOrder() {
		fail("Not yet implemented");
	}

}
