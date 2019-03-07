package com.mobilelive.nikhil.retailmart.service;

import com.mobilelive.nikhil.retailmart.domain.Product;
import com.mobilelive.nikhil.retailmart.wrapper.ProductWrapper;
import java.util.List;

/**
 * service interface for product lifecycle related events
 */
public interface ProductService {
    /**
     * get a product based on id. Throws {@link com.mobilelive.nikhil.retailmart.exception.ProductNotFoundException}
     * @param id
     * @return Product
     */
     Product getProduct(Long id);

    /**
     * create a new Product
     * @param productWrapper
     * @return
     */
     Long createProduct(ProductWrapper productWrapper);

    /**
     * update a product if present in db . Throws {@link com.mobilelive.nikhil.retailmart.exception.ProductNotFoundException}
     * @param productWrapper
     * @return updated Product
     */
     Product updateProduct(ProductWrapper productWrapper);

    /**
     * api to get all products in system
     * @return java.util.List<Product>
     */
     List<Product> getProducts();

    /**
     * api to delete a product if present in db. Throws {@link com.mobilelive.nikhil.retailmart.exception.ProductNotFoundException}
     * @param id
     * @return
     */
     void deleteProduct(Long id);
}
