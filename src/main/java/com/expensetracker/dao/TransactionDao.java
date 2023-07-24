package com.expensetracker.dao;

import com.expensetracker.model.Transaction;
import com.expensetracker.wrapper.TransactionWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionDao extends JpaRepository<Transaction, Integer> {

    TransactionWrapper getTransactionById(@Param("id") Integer id);

    List<TransactionWrapper> getAllTransaction();

}
