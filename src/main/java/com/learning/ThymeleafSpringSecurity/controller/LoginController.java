package com.learning.ThymeleafSpringSecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
//@RequestMapping("/") // added later for the default page
public class LoginController {

/*
    @GetMapping("/")
    public String showHome() {

        //taking to the login page
        //later changed so that it redirects to the home.html where the user info  would popup
        return "home";
    }

 */


//brings up login page
    @GetMapping("/showMyLoginPage") // login // showMyLoginPage
    public String showMyLoginPage() {

        return "fancy-login";

    }

    //  request mapping for /access-denied
    @GetMapping("/access-denied")
    public String showAccessDenied() {

        return "access-denied";

    }
}
