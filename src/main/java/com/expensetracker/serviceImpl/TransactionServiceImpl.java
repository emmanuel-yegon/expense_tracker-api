package com.expensetracker.serviceImpl;

import com.expensetracker.constants.ExpenseConstants;
import com.expensetracker.dao.TransactionDao;
import com.expensetracker.jwt.JwtFilter;
import com.expensetracker.model.Category;
import com.expensetracker.model.Transaction;
import com.expensetracker.model.User;
import com.expensetracker.service.TransactionService;
import com.expensetracker.utils.ExpenseUtils;
import com.expensetracker.wrapper.TransactionWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionDao transactionDao;

    @Autowired
    JwtFilter jwtFilter;


    @Override
    public ResponseEntity<String> addNewTransaction(Integer categoryId, Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                if (validateTransactionMap(requestMap, false)) {
                    transactionDao.save(getTransactionFromMap(requestMap, false));
                    return ExpenseUtils.getResponseEntity("Transaction Added Successfully", HttpStatus.OK);
                }
                return ExpenseUtils.getResponseEntity(ExpenseConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            } else
                return ExpenseUtils.getResponseEntity(ExpenseConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ExpenseUtils.getResponseEntity(ExpenseConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateTransactionMap(Map<String, String> requestMap, boolean validateId) {
        if (requestMap.containsKey("note")) {
            if (requestMap.containsKey("id") && validateId) {
                return true;
            } else return !validateId;
        }
        return false;
    }

    private Transaction getTransactionFromMap(Map<String, String> requestMap, boolean isAdd) {
        User user = new User();
        user.setId(Integer.parseInt(requestMap.get("userId")));

        Category category = new Category();
        category.setId(Integer.parseInt(requestMap.get("categoryId")));

        Transaction transaction = new Transaction();
        if (isAdd) {
            transaction.setId(Integer.parseInt(requestMap.get("id")));
        }
        transaction.setUser(user);
        transaction.setCategory(category);
        transaction.setAmount(Double.valueOf(requestMap.get("amount")));
        transaction.setNote(requestMap.get("note"));
        transaction.setTransactionDate(LocalDate.parse(requestMap.get("transactionDate")));
        return transaction;
    }

    @Override
    public ResponseEntity<TransactionWrapper> getTransactionById(Integer id) {
        try {
            return new ResponseEntity<>(transactionDao.getTransactionById(id), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new TransactionWrapper(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<TransactionWrapper>> getAllTransaction() {
        try {
            return new ResponseEntity<>(transactionDao.getAllTransaction(), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
