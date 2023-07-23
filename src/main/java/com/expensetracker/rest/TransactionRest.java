package com.expensetracker.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RequestMapping("/category/{categoryId}/transaction")
public interface TransactionRest {

    @PostMapping(path = "/add")
    ResponseEntity<String> addNewTransaction(@PathVariable("categoryId") Integer categoryId, @RequestBody Map<String,String> requestMap);
}
