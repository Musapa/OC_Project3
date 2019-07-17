package com.openclassrooms.shopmanager.order;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.WebApplicationContext;

import com.openclassrooms.shopmanager.product.Product;
import com.openclassrooms.shopmanager.product.ProductModel;
import com.openclassrooms.shopmanager.product.ProductRepository;
import com.openclassrooms.shopmanager.product.ProductService;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import javax.validation.Valid;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webContext;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private OrderRepository orderRepository;

	private Product product;

	@Before
	public void setupMockmvc() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
	}

	@Before
	public void createProduct() {

		product = new Product();
		product.setId(1L);
		product.setName("First product");
		product.setDescription("First discription");
		product.setDetails("First details");
		product.setPrice(15);
		product.setQuantity(10);

		product = productRepository.save(product);

	}

	@After
	public void deleteProduct() {
		productRepository.delete(product);
	}

	@Test
	public void addToCartSuccessTest() throws Exception {
		mockMvc.perform(post("/order/addToCart").param("productId", product.getId().toString()))
				.andExpect(view().name("redirect:/order/cart")).andExpect(model().errorCount(0))
				.andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/order/cart"));
	}

	/*
	 * @Test public void addToCartFailTest() throws Exception { // when we passed
	 * invalidID there is internal server error "5xx"
	 * mockMvc.perform(post("/order/addToCart").param("productId",
	 * "100")).andExpect(status().is5xxServerError()); assertTrue(true); }
	 */
	@Test
	public void addToCartFailTest() throws Exception {
		mockMvc.perform(post("/order/addToCart").param("productId", "100")).andExpect(view().name("error"))
				.andExpect(status().is5xxServerError());
	}

	@Test
	public void removeFromCartTest() throws Exception {
		mockMvc.perform(post("/order/removeFromCart").param("productId", product.getId().toString()))
				.andExpect(view().name("redirect:/order/cart")).andExpect(model().errorCount(0))
				.andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/order/cart"));
	}

	@Test
	public void createValidOrderTest() throws Exception {
		mockMvc.perform(post("/order").param("order", "1L")).andExpect(view().name("orderCompleted"))
				.andExpect(model().errorCount(0)).andExpect(status().isOk());
	}

	@Test
	public void createEmptyOrderTest() throws Exception {
		orderRepository.clear(); // clear orderRepository to force error
		// order class has no validation so you can pass invalid bean properties
		mockMvc.perform(post("/order").param("nameX", "hello")).andExpect(view().name("order"))
				.andExpect(model().errorCount(1)).andExpect(status().isOk());
	}

}
