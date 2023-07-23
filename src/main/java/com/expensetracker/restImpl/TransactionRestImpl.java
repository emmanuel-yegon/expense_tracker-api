package com.expensetracker.restImpl;

import com.expensetracker.constants.ExpenseConstants;
import com.expensetracker.rest.TransactionRest;
import com.expensetracker.service.TransactionService;
import com.expensetracker.utils.ExpenseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TransactionRestImpl implements TransactionRest {


    @Autowired
    TransactionService transactionService;

    @Override
    public ResponseEntity<String> addNewTransaction(Integer categoryId, Map<String, String> requestMap) {
        try {
            return transactionService.addNewTransaction(categoryId, requestMap);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return ExpenseUtils.getResponseEntity(ExpenseConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
