package com.mobilelive.nikhil.retailmart.repository;

import com.mobilelive.nikhil.retailmart.domain.Product;
import org.springframework.data.repository.CrudRepository;

/**
 * repository for data transfer between application and persistent storage for Product entity
 */
public interface ProductRepository extends CrudRepository<Product,Long> {
}
