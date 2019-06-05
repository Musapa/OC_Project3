package com.openclassrooms.shopmanager.order;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.openclassrooms.shopmanager.product.Product;
import com.openclassrooms.shopmanager.product.ProductService;

import static org.mockito.Mockito.when;


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
		fail("Not yet implemented");
	}
		
	@Test
	public void removeFromCart() {
		
		Product product = new Product();
		product.setId(1L);

		when(productService.getByProductId(1L)).thenReturn(product);
		orderService.addToCart(product.getId());

		orderService.removeFromCart(product.getId());

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
