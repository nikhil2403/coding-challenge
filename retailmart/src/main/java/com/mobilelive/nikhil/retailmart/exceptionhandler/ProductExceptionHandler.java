package com.mobilelive.nikhil.retailmart.exceptionhandler;

import com.mobilelive.nikhil.retailmart.exception.ProductNotFoundException;
import com.mobilelive.nikhil.retailmart.wrapper.ErrorWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.SQLException;

@ControllerAdvice
public class ProductExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(ProductExceptionHandler.class);

    @ExceptionHandler({ ProductNotFoundException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    private ErrorWrapper handleExceltion(ProductNotFoundException exp){
        log.error(exp.getLocalizedMessage());
        return new ErrorWrapper(exp.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ IllegalArgumentException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    private ErrorWrapper handleException(IllegalArgumentException exp){
        log.error(exp.getLocalizedMessage());
        return new ErrorWrapper("request not properly formatted.",HttpStatus.BAD_REQUEST);
    }
}
