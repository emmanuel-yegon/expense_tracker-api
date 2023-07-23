package com.expensetracker.service;

import com.expensetracker.wrapper.CategoryWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface CategoryService {

    ResponseEntity<String> addNewCategory(Map<String, String> requestMap);

    ResponseEntity<List<CategoryWrapper>> getAllCategory();

    ResponseEntity<CategoryWrapper> getCategoryById(Integer id);

    ResponseEntity<String> updateCategory(Map<String, String> requestMap);

}
