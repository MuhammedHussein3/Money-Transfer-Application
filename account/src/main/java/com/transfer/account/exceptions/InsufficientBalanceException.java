package com.transfer.account.exceptions;

public class InsufficientBalanceException extends RuntimeException{

    public InsufficientBalanceException(){
        super();
    }

    public InsufficientBalanceException(String msg){
        super(msg);
    }
}
