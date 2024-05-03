package com.pallabi.scheduler.exception;

public class CustomerNotFoundException extends Throwable{
    public CustomerNotFoundException(String message, Throwable throwable){
        super(message, throwable);
    }
}