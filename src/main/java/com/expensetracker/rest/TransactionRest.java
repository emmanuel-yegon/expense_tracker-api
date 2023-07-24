package com.expensetracker.rest;

import com.expensetracker.wrapper.TransactionWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/transaction")
public interface TransactionRest {

    @PostMapping(path = "/category/{categoryId}/add")
    ResponseEntity<String> addNewTransaction(@PathVariable("categoryId") Integer categoryId, @RequestBody Map<String, String> requestMap);

    @GetMapping(path = "/getById/{id}")
    ResponseEntity<TransactionWrapper> getTransactionById(@PathVariable Integer id);

    @GetMapping(path = "/get")
    ResponseEntity<List<TransactionWrapper>> getAllTransaction();

}
