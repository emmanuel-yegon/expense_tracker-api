package com.expensetracker.restImpl;

import com.expensetracker.constants.ExpenseConstants;
import com.expensetracker.rest.CategoryRest;
import com.expensetracker.service.CategoryService;
import com.expensetracker.utils.ExpenseUtils;
import com.expensetracker.wrapper.CategoryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class CategoryRestImpl implements CategoryRest {

    @Autowired
    CategoryService categoryService;

    @Override
    public ResponseEntity<String> addNewCategory(Map<String, String> requestMap) {
        try {
            return categoryService.addNewCategory(requestMap);
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return ExpenseUtils.getResponseEntity(ExpenseConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<CategoryWrapper>> getAllCategory() {
        try {
            return  categoryService.getAllCategory();
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<CategoryWrapper> getCategoryById(Integer id) {
        try {
            return categoryService.getCategoryById(id);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return  new ResponseEntity<>(new CategoryWrapper(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateCategory(Map<String, String> requestMap) {
        try {
            return categoryService.updateCategory(requestMap);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return ExpenseUtils.getResponseEntity(ExpenseConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
