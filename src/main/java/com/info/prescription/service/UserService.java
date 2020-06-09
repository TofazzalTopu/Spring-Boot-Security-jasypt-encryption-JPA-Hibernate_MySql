package com.info.prescription.service;


import com.info.prescription.model.User;
import com.info.prescription.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;


    public List<User> getUserList() {

        List<User> userList = userRepository.findAll();
        return userList;
    }

    public boolean saveUser(User user) {

        try {
            User savedUser = userRepository.save(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }


    public boolean isLoggedIn(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean isLoggedIn = false;
        if (principal instanceof UserDetails) {
            String username = ((UserDetails)principal).getUsername();
            isLoggedIn = true;
        } else {
            String username = principal.toString();
            isLoggedIn = false;
        }
        return isLoggedIn;
    }

}
