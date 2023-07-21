package com.expensetracker.serviceImpl;

import com.expensetracker.constants.ExpenseConstants;
import com.expensetracker.dao.CategoryDao;
import com.expensetracker.jwt.JwtFilter;
import com.expensetracker.model.Category;
import com.expensetracker.model.User;
import com.expensetracker.service.CategoryService;
import com.expensetracker.utils.ExpenseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> addNewCategory(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()){
                if (validateCategoryMap(requestMap, false)){
                    categoryDao.save(getCategoryFromMap(requestMap, false));
                    return ExpenseUtils.getResponseEntity("Category Added Successfully", HttpStatus.OK);
                }
                return ExpenseUtils.getResponseEntity(ExpenseConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }else
                return ExpenseUtils.getResponseEntity(ExpenseConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return ExpenseUtils.getResponseEntity(ExpenseConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private  boolean validateCategoryMap(Map<String, String> requestMap, boolean validateId){
        if (requestMap.containsKey("title")) {
            if (requestMap.containsKey("id") && validateId){
                return  true;
            } else if (!validateId){
                return true;
            }
        }
        return  false;
    }

    private Category getCategoryFromMap(Map<String, String> requestMap, boolean isAdd){
        User user = new User();
        user.setId(Integer.parseInt(requestMap.get("userId")));

        Category category = new Category();
        if (isAdd){
            category.setId(Integer.parseInt(requestMap.get("id")));
        }
        category.setUser(user);
        category.setTitle(requestMap.get("title"));
        category.setDescription(requestMap.get("description"));
        return category;
    }


}
