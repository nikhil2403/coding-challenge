package com.mobilelive.nikhil.retailmart.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.mobilelive.nikhil.retailmart.domain.Product;
import com.mobilelive.nikhil.retailmart.exception.ProductNotFoundException;
import com.mobilelive.nikhil.retailmart.service.ProductService;
import com.mobilelive.nikhil.retailmart.utils.ObjectConverter;
import com.mobilelive.nikhil.retailmart.wrapper.ProductWrapper;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Cacheable(value = "single")
    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ProductWrapper getProduct(@PathVariable Long id){
        Product product = productService.getProduct(id);
     return    ObjectConverter.convertCaseInsensitive(product,ProductWrapper.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long createProduct(@RequestBody ProductWrapper productWrapper){
       return productService.createProduct(productWrapper);
    }

@CachePut(key = "#productWrapper.id",value = "single")
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ProductWrapper updateProduct(@RequestBody ProductWrapper productWrapper){
        Product product = productService.updateProduct(productWrapper);
        return ObjectConverter.convertCaseInsensitive(product,ProductWrapper.class);
    }
@CacheEvict(value = "single")
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
    }

    //@CachePut(value = "all",key = "#root.methodName",unless = "#result ==null or result.size()==0")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductWrapper> getAllProducts(){
        List<Product> products = productService.getProducts();
      return   ObjectConverter.convertToList(products, new TypeReference<List<ProductWrapper>>(){});
    }

}
