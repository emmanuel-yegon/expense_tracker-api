package com.expensetracker.serviceImpl;

import com.expensetracker.constents.ExpenseConstants;
import com.expensetracker.dao.UserDao;
import com.expensetracker.model.User;
import com.expensetracker.service.UserService;
import com.expensetracker.utils.ExpenseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class UserServiceImpl  implements UserService {

    @Autowired
    UserDao userDao;


    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        log.info("Inside signup {}",requestMap);
        try {
            if(validateSignUpMap(requestMap)){
                User user = userDao.findByEmailId(requestMap.get("email"));
                if(Objects.isNull(user)){
                    userDao.save(getUserFromMap(requestMap));
                    return ExpenseUtils.getResponseEntity("Successfully Registered.",HttpStatus.OK);
                }else {
                    return ExpenseUtils.getResponseEntity("Email already exist",HttpStatus.BAD_REQUEST);
                }
            }
            else {
                return ExpenseUtils.getResponseEntity(ExpenseConstants.INVALID_DATA,HttpStatus.BAD_GATEWAY);
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }

        return ExpenseUtils.getResponseEntity(ExpenseConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateSignUpMap(Map<String, String> requestMap) {
        if(requestMap.containsKey("name") && requestMap.containsKey("contactNumber")
        && requestMap.containsKey("email") && requestMap.containsKey("password")){
            return true;
        }
        return false;
    }

    private User getUserFromMap(Map<String, String> requestMap) {
        User user = new User();
        user.setName(requestMap.get("name"));
        user.setContactNumber(requestMap.get("contactNumber"));
        user.setEmail(requestMap.get("email"));
        user.setPassword(requestMap.get("password"));
        return user;

    }

}
