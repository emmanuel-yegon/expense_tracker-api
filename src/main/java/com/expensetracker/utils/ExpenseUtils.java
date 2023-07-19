package com.expensetracker.utils;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ExpenseUtils {

    public ExpenseUtils() {

    }

    public static ResponseEntity<String> getResponseEntity(String responseMessage, HttpStatus httpStatus){
        return new ResponseEntity<String>("{\"message\":\"" + responseMessage + "\"}",httpStatus);
    }
}
