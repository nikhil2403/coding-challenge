package com.mobilelive.nikhil.retailmart.exception;

/**
 * Exception class to handle case if product is not found
 */
public class ProductNotFoundException extends RuntimeException {
    private String message;

   public ProductNotFoundException(){

   }
   public ProductNotFoundException(String message){
        super(message);
        this.message = message;
   }
}
