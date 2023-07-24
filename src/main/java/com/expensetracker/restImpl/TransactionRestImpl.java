package com.expensetracker.restImpl;

import com.expensetracker.constants.ExpenseConstants;
import com.expensetracker.rest.TransactionRest;
import com.expensetracker.service.TransactionService;
import com.expensetracker.utils.ExpenseUtils;
import com.expensetracker.wrapper.TransactionWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class TransactionRestImpl implements TransactionRest {

    @Autowired
    TransactionService transactionService;


    @Override
    public ResponseEntity<String> addNewTransaction(Integer categoryId, Map<String, String> requestMap) {
        try {
            return transactionService.addNewTransaction(categoryId, requestMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ExpenseUtils.getResponseEntity(ExpenseConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<TransactionWrapper> getTransactionById(Integer id) {
        try {
            return transactionService.getTransactionById(id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new TransactionWrapper(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<TransactionWrapper>> getAllTransaction() {
        try {
            return transactionService.getAllTransaction();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
