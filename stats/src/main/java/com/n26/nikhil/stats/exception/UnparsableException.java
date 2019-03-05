package com.n26.nikhil.stats.exception;

import org.springframework.http.HttpStatus;

public class UnparsableException extends RuntimeException {
    private HttpStatus status;

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public UnparsableException(HttpStatus status){
        super();
        this.status = status;
    }
   public  UnparsableException(){

    }
}
