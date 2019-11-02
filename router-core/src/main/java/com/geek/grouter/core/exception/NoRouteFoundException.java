package com.geek.grouter.core.exception;

public class NoRouteFoundException extends RuntimeException{
    public NoRouteFoundException(String detailMessage){
        super(detailMessage);
    }
}
