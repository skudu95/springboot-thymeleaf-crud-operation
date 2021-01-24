package com.learning.ThymeleafSpringSecurity.controller;

import com.learning.ThymeleafSpringSecurity.entity.User;
import com.learning.ThymeleafSpringSecurity.model.MyUserDetails;
import com.learning.ThymeleafSpringSecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.logging.Logger;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private UserService userService;

    private Logger logger = Logger.getLogger(getClass().getName());

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {

        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }


//getting form for registration
    @GetMapping("/showRegistrationForm") //registrationForm / register
    public String showMyLoginPage(Model model) {

        model.addAttribute("myUserDetails", new MyUserDetails());

        return "registration-form";
    }


// posting the data
    @PostMapping("/processRegistrationForm") //registrationForm / register
    public String processRegistrationForm(
            @Valid @ModelAttribute("myUserDetails") MyUserDetails myUserDetails,
            BindingResult theBindingResult,
            Model model) {

        String userName = myUserDetails.getUserName();
        logger.info("Processing registration form for: " + userName);

        // form validation
        if (theBindingResult.hasErrors()){
            return "registration-form";
        }

        // check the database if user already exists
        User existing = userService.findByUserName(userName);
        if (existing != null){
            model.addAttribute("myUserDetails", new MyUserDetails());

            //shows up error
            model.addAttribute("registrationError", "User name already exists.");

            // popping up error message in console
            logger.warning("User name already exists.");
            return "registration-form";
        }

        // creates new user account
        userService.save(myUserDetails);

        logger.info("Successfully created user: " + userName);

        return "registration-confirmation";
    }

}
