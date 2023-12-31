package com.expensetracker.model;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;

@NamedQuery(name = "Category.getAllCategory", query = "select new com.expensetracker.wrapper.CategoryWrapper(c.id,c.title,c.description,c.user.id) from Category c ")

@NamedQuery(name = "Category.getCategoryById", query = "select new com.expensetracker.wrapper.CategoryWrapper(c.id,c.title,c.description) from Category c where c.id=:id")


@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "category", schema = "expense")
public class Category implements Serializable {

    private static final long  serialVersionUID = 1l;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "totalExpense")
    private Integer totalExpense;

    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "user_fk", nullable = false)
    private User user;

}
