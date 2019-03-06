package com.mobilelive.nikhil.retailmart.service.impl;

import com.mobilelive.nikhil.retailmart.exception.ProductNotFoundException;
import com.mobilelive.nikhil.retailmart.repository.ProductRepository;
import com.mobilelive.nikhil.retailmart.service.ProductService;
import com.mobilelive.nikhil.retailmart.wrapper.ProductWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import com.mobilelive.nikhil.retailmart.domain.Product;
import org.mockito.stubbing.Answer;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceImplTest {
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductService productService = new ProductServiceImpl();

    @Test
    public void getProduct() {

        when(productRepository.findById(1l)).thenReturn(Optional.of(new Product()));
        Product product = productService.getProduct(1l);
        assertNotNull(product);
    }
    @Test(expected = ProductNotFoundException.class)
    public void getProductThrowsException() {

        when(productRepository.findById(1l)).thenThrow(new ProductNotFoundException());
         productService.getProduct(1l);

    }

    @Test
    public void createProduct() {
        ProductWrapper productWrapper = new ProductWrapper();
        productWrapper.setId(1L);

        when(productRepository.save(any(Product.class))).then((Answer<Product>) invocationOnMock -> {
            Product p = new Product();
            p.setId(1L);
            return p;
        });
        long productId = productService.createProduct(productWrapper);
        assertEquals(1l,productId);
    }

    @Test
    public void updateProduct() {
        ProductWrapper productWrapper = new ProductWrapper();
        productWrapper.setId(1L);

        when(productRepository.findById(1l)).thenReturn(Optional.of(new Product()));

        when(productRepository.save(any(Product.class))).then((Answer<Product>) invocationOnMock -> {
            Product p = new Product();
            p.setId(1L);
            return p;
        });
        Product product = productService.updateProduct(productWrapper);
        assertNotNull(product);
    }

    @Test
    public void deleteProduct() {
        when(productRepository.findById(1l)).thenReturn(Optional.of(new Product()));
        productService.deleteProduct(1L);
    }
}