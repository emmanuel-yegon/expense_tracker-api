package com.expensetracker.dao;

import com.expensetracker.model.Category;
import com.expensetracker.wrapper.CategoryWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryDao  extends JpaRepository<Category, Integer> {

    List<CategoryWrapper> getAllCategory();

    CategoryWrapper getCategoryById(@Param("id") Integer id);

}
