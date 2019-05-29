package com.openclassrooms.shopmanager.product;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.openclassrooms.shopmanager.order.Cart;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Take this test method as a template to write your test methods for ProductService and OrderService.
 * A test method must check if a definite method does its job:
 *
 * Naming follows this popular convention : http://osherove.com/blog/2005/4/3/naming-standards-for-unit-tests.html
 */


@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    @InjectMocks
    ProductService productService;

    @Mock
    ProductRepository productRepository;

    @Test
    public void getAllProducts_DbHasData_allDataReturned(){

        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("First product");

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("First product");

        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

        List<Product> products = productService.getAllProducts();

        assertEquals(2, products.size());
        assertEquals(1L, products.get(0).getId() , 0);
        assertEquals(2L, products.get(1).getId() , 0);
    }
    
    @Test
    public void getAllAdminProducts(){
        
    	Product product1 = new Product();
        product1.setId(1L);
        product1.setName("First product");

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("First product");

        
        when(productRepository.findAllByOrderByIdDesc()).thenReturn(Arrays.asList(product1, product2));

        List<Product> products = productService.getAllAdminProducts();

        assertEquals(2, products.size());
        assertEquals(1L, products.get(0).getId() , 0);
        assertEquals(2L, products.get(1).getId() , 0);
        
    }
    
    @Test
    public void getByProductId(){
    	
    	Product product = new Product();
        product.setId(1L);
        product.setName("First product");
        
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        
        Product productFound = productService.getByProductId(product.getId());
        assertEquals(1L, productFound.getId(), 0);
		assertEquals("First product", productFound.getName());
    }
    
    @Test
    public void createProduct(){
    	
    	Product product = new Product();
    	
    	product.setId(1L);
        product.setName("First product"); 
        product.setDescription("First discription");
        product.setDetails("First details");
        product.setPrice(15);
        product.setQuantity(10);
        
        when(productRepository.save(any(Product.class))).thenReturn(product);
        
        ProductModel productModel = new ProductModel();
        
        productModel.setId(product.getId());
        productModel.setName(product.getName()); 
        productModel.setDescription(product.getDescription());
        productModel.setDetails(product.getDetails());
        productModel.setPrice(String.valueOf(product.getPrice()));          
        productModel.setQuantity(String.valueOf(product.getQuantity()));
        
        //Product productCreate = productService.createProduct();
        Product productCreate = productService.createProduct(productModel);
        
        assertEquals(1L, productCreate.getId(), 0);
		assertEquals("First product", productCreate.getName());
		assertEquals("First discription", productCreate.getDescription());
		assertEquals("First details", productCreate.getDetails());
		assertEquals(15, productCreate.getPrice(), 0);
		assertEquals(10, productCreate.getQuantity(), 0);	
    }  
    
    @Test
    public void deleteProduct(){
        
    	Long productIdTest = 1L;
    	
    	productService.deleteProduct(productIdTest);
    	verify(productRepository, times(1)).deleteById(productIdTest);
        
    }
   
    @Test
    public void updateProductQuantitiesDelete(){
    	
    	Product product = new Product();
        product.setId(1L);
        product.setName("First product");
        product.setQuantity(1);

        Cart cart = new Cart();
        cart.addItem(product, 1);
        
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));       
    	productService.updateProductQuantities(cart);
    	verify(productRepository, times(1)).delete(product);
  
    }
    
    @Test
    public void updateProductQuantitiesSave(){
    	
    	Product product = new Product();
        product.setId(1L);
        product.setName("First product");
        product.setQuantity(10);

        Cart cart = new Cart();
        cart.addItem(product, 1);
        
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));       
    	productService.updateProductQuantities(cart);
    	verify(productRepository, times(1)).save(product);
  
    } 
    
}
