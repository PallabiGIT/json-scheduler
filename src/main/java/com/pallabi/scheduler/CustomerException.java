package com.pallabi.scheduler;

public class CustomerException extends Throwable{
    public CustomerException(String message, Throwable throwable){
        super(message, throwable);
    }
}