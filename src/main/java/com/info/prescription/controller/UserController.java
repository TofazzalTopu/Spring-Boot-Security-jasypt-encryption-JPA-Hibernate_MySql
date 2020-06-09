package com.info.prescription.controller;

import com.info.prescription.model.User;
import com.info.prescription.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping(value = "/")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = {"/signup"}, method = RequestMethod.GET)
    public String main(Model model) {
        return "user/signup";
    }

    @RequestMapping(value = "/executeSaveUser", method = RequestMethod.POST)
    public String executeSaveUser(Model model, @ModelAttribute("user") User user) {

        try {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            boolean isSaved = userService.saveUser(user);

            if (isSaved) {
                model.addAttribute("user", user);
                model.addAttribute("success", "User has been created successfully");
                return "user/signup";
            } else {
                model.addAttribute("error", "User not created!");
                return "user/signup";
            }

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "User not saved!");
            return "user/signup";
        }
    }

}
