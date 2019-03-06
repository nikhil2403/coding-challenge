package com.mobilelive.nikhil.retailmart.service.impl;

import com.mobilelive.nikhil.retailmart.domain.Product;
import com.mobilelive.nikhil.retailmart.exception.ProductNotFoundException;
import com.mobilelive.nikhil.retailmart.exceptionhandler.ProductExceptionHandler;
import com.mobilelive.nikhil.retailmart.repository.ProductRepository;
import com.mobilelive.nikhil.retailmart.service.ProductService;
import com.mobilelive.nikhil.retailmart.utils.ObjectConverter;
import com.mobilelive.nikhil.retailmart.wrapper.ProductWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);
   @Autowired
   private ProductRepository productRepository;

    /**
     * get a product based on id. Throws {@link ProductNotFoundException}
     *
     * @param id
     * @return Product
     */
    @Override
    public Product getProduct(Long id) {
        log.info("request to get product with id {}",id);
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()){
            return product.get();
        }
        else {
            throw new ProductNotFoundException(String.format("No product found with id %d",id));
        }
    }

    /**
     * create a new Product
     *
     * @param productWrapper
     * @return
     */
    @Override
    public Long createProduct(ProductWrapper productWrapper) {
        log.info("request to save product {}",productWrapper);
        Product product = ObjectConverter.convertCaseInsensitive(productWrapper, Product.class);
         productRepository.save(product);
         return product.getId();
    }

    /**
     * update a product if present in db . Throws {@link ProductNotFoundException}
     *
     * @param productWrapper
     * @return updated Product
     */
    @Override
    public Product updateProduct(ProductWrapper productWrapper) {
        log.info("request to update product {} ",productWrapper);
        Optional<Product> productOptional = productRepository.findById(productWrapper.getId());
        if (!productOptional.isPresent()){
            throw new ProductNotFoundException(String.format("No product found with id %d",productWrapper.getId()));
        }
        else {
           Product product = ObjectConverter.convertCaseInsensitive(productWrapper, Product.class);
           return  productRepository.save(product);
        }
    }

    /**
     * api to get all products in system
     *
     * @return java.util.List<Product>
     */
    @Override
    public List<Product> getProducts() {
        System.out.printf(System.getProperty("java.class.path"));
        log.info("request to get all products");
        return (List<Product>) productRepository.findAll();
    }

    /**
     * api to delete a product if present in db. Throws {@link ProductNotFoundException}
     *
     * @param id
     * @return
     */
    @Override
    public void deleteProduct(Long id) {
        log.info("request to delete prodcut with id {}",id);
        Optional<Product> productOptional = productRepository.findById(id);
        if (!productOptional.isPresent()){
            throw new ProductNotFoundException(String.format("No product found with id %d",id));
        }
        else {
             productRepository.deleteById(id);
        }
    }
}
