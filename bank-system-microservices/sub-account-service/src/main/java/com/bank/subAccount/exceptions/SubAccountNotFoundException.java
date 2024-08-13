package com.bank.subAccount.exceptions;

public class SubAccountNotFoundException extends RuntimeException{

    public SubAccountNotFoundException(){
        super();
    }

    public SubAccountNotFoundException(String msg){
        super(msg);
    }
}
