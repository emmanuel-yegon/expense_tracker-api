package com.expensetracker.wrapper;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class TransactionWrapper {

    Integer id;
    Double amount;
    String note;
    LocalDate transaction_date;
    Integer userId;
    Integer categoryId;

    public TransactionWrapper(Integer id, Double amount, String note, LocalDate transaction_date, Integer userId, Integer categoryId) {
        this.id = id;
        this.amount = amount;
        this.note = note;
        this.transaction_date = transaction_date;
        this.userId = userId;
        this.categoryId = categoryId;
    }

    public TransactionWrapper(Integer id, Double amount, String note, LocalDate transaction_date) {
        this.id = id;
        this.amount = amount;
        this.note = note;
        this.transaction_date = transaction_date;
    }

}
