package com.openclassrooms.shopmanager.product;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

//ProductController.class which loads the configuration we need for this particular test
@WebMvcTest(ProductController.class)

//The SpringRunner is essentially the entry-point to start using the Spring Test framework
@RunWith(SpringRunner.class)

public class ProductControllerTest {

	/* It loads all the application beans and controllers into the context. */
	@Autowired
	private WebApplicationContext webContext;

	/*
	 * MockMvc provides support for Spring MVC testing. It encapsulates all web
	 * application beans and make them available for testing.
	 */
	@Autowired
	private MockMvc mockMvc;

	/*
	 * We can use the @MockBean to add mock objects to the Spring application
	 * context. The mock will replace any existing bean of the same type in the
	 * application context. If no bean of the same type is defined, a new one will
	 * be added.
	 */
	@MockBean
	private ProductService productService;

	/* initialize Mockmvc */
	@Before
	public void setupMockmvc() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
	}

	/* check if we can create a valid product */
	@Test
	public void createValidProduct() throws Exception {
		mockMvc.perform(post("/admin/product").param("name", "Nokia").param("price", "2.0").param("quantity", "10"))
				.andExpect(view().name("redirect:/admin/products")).andExpect(model().errorCount(0))
				.andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/admin/products"));
	}

	/* check if error is thrown when we post product without name */
	@Test
	public void createProductwithoutName() throws Exception {
		mockMvc.perform(post("/admin/product").param("price", "2.0").param("quantity", "10"))
				.andExpect(view().name("product")).andExpect(model().errorCount(1))
				.andExpect(model().attributeHasFieldErrors("product", "name")).andExpect(status().isOk());
	}

	/* check if error is thrown when we post product without price */
	@Test
	public void createProductwithoutPrice() throws Exception {
		mockMvc.perform(post("/admin/product").param("name", "Nokia").param("quantity", "10"))
				.andExpect(view().name("product")).andExpect(model().errorCount(1))
				.andExpect(model().attributeHasFieldErrors("product", "price")).andExpect(status().isOk());
	}

	/* check if error is thrown when we post product without quantity */
	@Test
	public void createProductwithoutQuantity() throws Exception {
		mockMvc.perform(post("/admin/product").param("name", "Nokia").param("price", "2.0"))
				.andExpect(view().name("product")).andExpect(model().errorCount(1))
				.andExpect(model().attributeHasFieldErrors("product", "quantity")).andExpect(status().isOk());
	}

	/* check if error is thrown when we post product withit invalid quantity */
	@Test
	public void createProductwithoutInvalidQuantity() throws Exception {
		mockMvc.perform(post("/admin/product").param("name", "Nokia").param("price", "2.0").param("quantity", "aa"))
				.andExpect(view().name("product")).andExpect(model().errorCount(1))
				.andExpect(model().attributeHasFieldErrors("product", "quantity")).andExpect(status().isOk());
	}

	/* check if error is thrown when we post product withit invalid price */
	@Test
	public void createProductwithoutInvalidPrice() throws Exception {
		mockMvc.perform(post("/admin/product").param("name", "Nokia").param("price", "aa").param("quantity", "10"))
				.andExpect(view().name("product")).andExpect(model().errorCount(1))
				.andExpect(model().attributeHasFieldErrors("product", "price")).andExpect(status().isOk());
	}
}