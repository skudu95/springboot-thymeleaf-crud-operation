package com.learning.ThymeleafSpringSecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

@Controller
public class HomeController {

    @GetMapping("/")
    public String showHome() {


        //taking to the login page
        //later changed so that it redirects to the home.html where the user info  would popup
        return "home";
    }
}
