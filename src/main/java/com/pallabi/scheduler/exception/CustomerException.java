package com.pallabi.scheduler.exception;

public class CustomerException extends Throwable{
    public CustomerException(String message, Throwable throwable){
        super(message, throwable);
    }
}