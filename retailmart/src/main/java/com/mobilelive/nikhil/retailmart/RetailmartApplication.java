package com.mobilelive.nikhil.retailmart;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.mobilelive.nikhil.retailmart.wrapper.ProductWrapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RetailmartApplication implements RequestHandler<ProductWrapper,ProductWrapper> {

    public static void main(String[] args) {
        SpringApplication.run(RetailmartApplication.class, args);
    }

    /**
     * Handles a Lambda Function request
     *
     * @param input   The Lambda Function input
     * @param context The Lambda execution environment context object.
     * @return The Lambda Function output
     */
    @Override
    public ProductWrapper handleRequest(ProductWrapper input, Context context) {
        //SpringApplication.run(RetailmartApplication.class, args);
        return null;
    }
}

