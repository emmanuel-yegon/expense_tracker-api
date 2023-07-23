package com.expensetracker.serviceImpl;

import com.expensetracker.constants.ExpenseConstants;
import com.expensetracker.dao.CategoryDao;
import com.expensetracker.jwt.JwtFilter;
import com.expensetracker.model.Category;
import com.expensetracker.model.User;
import com.expensetracker.service.CategoryService;
import com.expensetracker.utils.ExpenseUtils;
import com.expensetracker.wrapper.CategoryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    @Override
    public ResponseEntity<List<CategoryWrapper>> getAllCategory() {
        try {
            return new ResponseEntity<>(categoryDao.getAllCategory(),HttpStatus.OK);
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<CategoryWrapper> getCategoryById(Integer id) {
        try {
            return new ResponseEntity<>(categoryDao.getCategoryById(id), HttpStatus.OK);
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new CategoryWrapper(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateCategory(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                if (validateCategoryMap(requestMap, true)){
                    Optional<Category> optional = categoryDao.findById(Integer.parseInt(requestMap.get("id")));
                    if (!optional.isEmpty()){
                        Category category = getCategoryFromMap(requestMap, true);
                        categoryDao.save(category);
                        return ExpenseUtils.getResponseEntity("Category Updated Successfully.", HttpStatus.OK);
                    } else {
                        return ExpenseUtils.getResponseEntity("Category id does not exist.",HttpStatus.OK);
                    }
                }else  {
                    return ExpenseUtils.getResponseEntity(ExpenseConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
                }
            }else {
                return ExpenseUtils.getResponseEntity(ExpenseConstants.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return  ExpenseUtils.getResponseEntity(ExpenseConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
