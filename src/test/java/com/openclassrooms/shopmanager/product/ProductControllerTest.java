package com.openclassrooms.shopmanager.product;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

//@WithMockUser(username="admin.exe", password="password",roles = "ADMIN")
@WebMvcTest(ProductController.class)
@RunWith(SpringRunner.class)
public class ProductControllerTest {

	@Autowired
	private WebApplicationContext webContext;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProductService productService;

	@Before
	public void setupMockmvc() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
	}

	@Test
	public void createValidProduct() throws Exception {
		//make http called
		mockMvc.perform(post("/admin/product").param("name", "Nokia").param("price", "2.0").param("quantity", "10")
				//csrf need or not????
				//andExpect what 
				.with(csrf())).andExpect(view().name("redirect:/admin/products")).andExpect(model().errorCount(0))
				.andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/admin/products"));

	}

	@Test
	public void createProductwithoutName() throws Exception {

	}

	@Test
	public void createProductwithoutPrice() throws Exception {

	}

	@Test
	public void createProductwithoutQuantity() throws Exception {

	}
}