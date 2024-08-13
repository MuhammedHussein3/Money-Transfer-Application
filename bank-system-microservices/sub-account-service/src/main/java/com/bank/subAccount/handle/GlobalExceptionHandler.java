package com.bank.subAccount.handle;

import com.bank.subAccount.exceptions.SubAccountNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(SubAccountNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleSubAccountNotFoundException(
            SubAccountNotFoundException exp
    ){
        logger.error("Insufficient balance for the transaction.");

        ExceptionResponse response = ExceptionResponse.builder()
                .businessErrorDescription("Not Found Error, please enter the SubAccount ID")
                .error(exp.getMessage())
                .build();
        return ResponseEntity
                .status(NOT_FOUND)
                .body(response);
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
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        var errors = new HashMap<String, Object>();
        e.getBindingResult().getAllErrors()
                .forEach(error ->{
                    var fieldName = ((FieldError)error).getField();
                    var errorMessage = error.getDefaultMessage();
                    errors.put(fieldName,errorMessage);

                });
        errors.put("status", String.valueOf(HttpStatus.BAD_REQUEST.value()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(errors));
    }
}
