package com.pallabi.scheduler.utils.exception;

public class CustomerException extends Throwable{
    public CustomerException(String message, Throwable throwable){
        super(message, throwable);
    }
}