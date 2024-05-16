package com.pallabi.scheduler.utils.exception;

public class CustomerNotFoundException extends Throwable{
    public CustomerNotFoundException(String message, Throwable throwable){
        super(message, throwable);
    }
}