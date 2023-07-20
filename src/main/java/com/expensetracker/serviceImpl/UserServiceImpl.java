package com.expensetracker.serviceImpl;

import com.expensetracker.constents.ExpenseConstants;
import com.expensetracker.dao.UserDao;
import com.expensetracker.jwt.CustomerUsersDetailsService;
import com.expensetracker.jwt.JwtFilter;
import com.expensetracker.jwt.JwtUtil;
import com.expensetracker.model.User;
import com.expensetracker.service.UserService;
import com.expensetracker.utils.ExpenseUtils;
import com.expensetracker.wrapper.UserWrapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.CachingUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class UserServiceImpl  implements UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    CustomerUsersDetailsService customerUsersDetailsService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        log.info("Inside signup {}",requestMap);
        try {
            if(validateSignUpMap(requestMap)){
                User user = userDao.findByEmailId(requestMap.get("email"));
                //Pattern pattern = Pattern.compile("^(.+)@(.+)$");
                if(Objects.isNull(user)){
                    //String hashedPassword = BCrypt.hashpw("password", BCrypt.gensalt(10));
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
        user.setEmail(requestMap.get("email").toLowerCase());
        user.setPassword(requestMap.get("password"));
        user.setStatus("false");
        user.setRole("user");
        return user;

    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        log.info("Inside login");
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("password"))
            );
            if (auth.isAuthenticated()){
                if (customerUsersDetailsService.getUserDetail().getStatus().equalsIgnoreCase("true")){
                    return new ResponseEntity<String>("{\"token\":\"" +
                            jwtUtil.generateToken(customerUsersDetailsService.getUserDetail().getEmail(),
                                    customerUsersDetailsService.getUserDetail().getRole()) + "\"}",
                            HttpStatus.OK);
                }
                else {
                    return new ResponseEntity<String>("{\"message\":\"" + "Wait for admin approval." + "\"}",
                            HttpStatus.BAD_REQUEST);
                }
            }
        }
        catch (Exception ex){
            log.error("{}",ex);
        }
        return new ResponseEntity<String>("{\"message\":\"" + "Bad Credentials." + "\"}",
                HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUser() {
        try {
            if (jwtFilter.isAdmin()){
                return new ResponseEntity<>(userDao.getAllUser(), HttpStatus.OK);
            } else {
                return  new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin()){
                Optional<User> optional = userDao.findById(Integer.parseInt(requestMap.get("id")));
                if (!optional.isEmpty()){
                    userDao.updateStatus(requestMap.get("status"), Integer.parseInt(requestMap.get("id")));
                    return  ExpenseUtils.getResponseEntity("User status updated successfully", HttpStatus.OK);
                } else {
                    ExpenseUtils.getResponseEntity("user id does not exist", HttpStatus.OK);
                }
            } else {
                return ExpenseUtils.getResponseEntity(ExpenseConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return ExpenseUtils.getResponseEntity(ExpenseConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
