package com.mobilelive.nikhil.retailmart.wrapper;

import org.springframework.http.HttpStatus;

public class ErrorWrapper {
    private String errorMessage;
    private HttpStatus httpStatus;

    public ErrorWrapper(String errorMessage, HttpStatus httpStatus) {
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    @Override
    public String toString() {
        return "ErrorWrapper{" +
                "errorMessage='" + errorMessage + '\'' +
                ", httpStatus=" + httpStatus +
                '}';
    }
}
