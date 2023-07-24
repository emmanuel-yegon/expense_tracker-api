package com.expensetracker.service;

import com.expensetracker.wrapper.TransactionWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface TransactionService {

    ResponseEntity<String> addNewTransaction(Integer categoryId, Map<String, String> requestMap);

    ResponseEntity<TransactionWrapper> getTransactionById(Integer id);

    ResponseEntity<List<TransactionWrapper>> getAllTransaction();

}
