package com.expensetracker.dao;

import com.expensetracker.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryDao  extends JpaRepository<Category, Integer> {

}
