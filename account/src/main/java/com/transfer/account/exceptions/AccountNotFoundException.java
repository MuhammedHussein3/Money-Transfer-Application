package com.transfer.account.exceptions;

public class AccountNotFoundException extends RuntimeException{

    public AccountNotFoundException(String msg) {
        super(msg);
    }

    public AccountNotFoundException(){
        super();
    }
}
