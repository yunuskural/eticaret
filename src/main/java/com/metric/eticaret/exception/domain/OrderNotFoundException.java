package com.metric.eticaret.exception.domain;

public class OrderNotFoundException extends RuntimeException{

    public OrderNotFoundException(String message){
    }

    public String orderNotFoundException(){
        return "Order not found";
    }
}
