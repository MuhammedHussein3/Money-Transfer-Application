package com.transfer.account.handle;

import com.transfer.account.exceptions.AccountNotFoundException;
import com.transfer.account.exceptions.InsufficientBalanceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ExceptionResponse> handlerInsufficientBalanceException(InsufficientBalanceException exp){
        logger.error("Insufficient balance for the transaction.");

        ExceptionResponse response = ExceptionResponse.builder()
                .businessErrorDescription("Not Found Error, please enter the correct Amount")
                .error(exp.getMessage())
                .build();
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleAccountNotFoundException(AccountNotFoundException exp) {
        logger.error("Account not found exception: {}", exp.getMessage(), exp);

        ExceptionResponse response = ExceptionResponse.builder()
                .businessErrorDescription("Not Found Error, please enter the correct AccountNumber")
                .error(exp.getMessage())
                .build();

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionResponse> handleDataIntegrityViolationException(DataIntegrityViolationException exp) {
        Throwable rootCause = exp.getRootCause();
        if (rootCause != null && rootCause.getMessage() != null &&
                rootCause.getMessage().contains("duplicate key value violates unique constraint")) {
            logger.error("Data integrity violation: {}", rootCause.getMessage(), exp);

            ExceptionResponse response = ExceptionResponse.builder()
                    .businessErrorDescription("A record with the same unique identifier already exists. Please use a different email address.")
                    .error("Duplicate key value error.")
                    .build();

            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }

        logger.error("Data integrity violation: {}", exp.getMessage(), exp);
        ExceptionResponse response = ExceptionResponse.builder()
                .businessErrorDescription("Data integrity violation. Please contact the admin.")
                .error(exp.getMessage())
                .build();

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception exp) {

        logger.error("Unhandled exception occurred: {}", exp.getMessage(), exp);


        ExceptionResponse response = ExceptionResponse.builder()
                .businessErrorDescription("Internal error, please contact the admin")
                .error("An unexpected error occurred.")
                .build();

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
