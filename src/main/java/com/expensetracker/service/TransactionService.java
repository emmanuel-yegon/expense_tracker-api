package com.expensetracker.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface TransactionService  {

    ResponseEntity<String> addNewTransaction(Integer categoryId, Map<String, String> requestMap);

}
