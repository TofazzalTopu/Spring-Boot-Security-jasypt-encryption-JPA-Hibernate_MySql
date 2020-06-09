package com.info.prescription.controller;

import com.info.prescription.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/")
public class LoginController {

    @Autowired
    UserService userService;

    // @Comment: This method will render login form to login the system.
    @RequestMapping(method = RequestMethod.GET, value = {"/login"})
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        boolean isLoggedIn = userService.isLoggedIn();
        if (isLoggedIn) {
            model.addAttribute("isLoggedIn", isLoggedIn);
            return "prescription/prescription_list";
        }
        model.addAttribute("isLoggedIn", isLoggedIn);
        return "login/login";
    }

    @RequestMapping(value = {"/index"}, method = RequestMethod.GET)
    public String index(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean isLoggedIn = userService.isLoggedIn();
        model.addAttribute("isLoggedIn", isLoggedIn);
        return "login/index";
    }
}
