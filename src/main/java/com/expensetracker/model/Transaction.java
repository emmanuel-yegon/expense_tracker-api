package com.expensetracker.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.time.LocalDate;

@NamedQuery(name = "Transaction.getAllTransaction", query = "select new com.expensetracker.wrapper.TransactionWrapper(t.id,t.amount,t.note,t.transactionDate,t.user.id,t.category.id) from Transaction t")

@NamedQuery(name = "Transaction.getTransactionById", query = "select new com.expensetracker.wrapper.TransactionWrapper(t.id,t.amount,t.note,t.transactionDate) from Transaction t where t.id=:id")


@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "transaction", schema = "expense")
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1l;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "note")
    private String note;

    @Column(name = "transactionDate")
    @JsonFormat(pattern = "yyyy-mm-dd")
    private LocalDate transactionDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_fk", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_fk", nullable = false)
    private Category category;


}
