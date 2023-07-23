package com.expensetracker.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.io.Serializable;



@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "transaction", schema = "expense")
public class Transaction  implements Serializable {

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


    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "user_fk", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "category_fk", nullable = false)
    private Category category;
}
