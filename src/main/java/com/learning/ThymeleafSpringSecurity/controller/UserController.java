package com.learning.ThymeleafSpringSecurity.controller;

import com.learning.ThymeleafSpringSecurity.dao.RoleRepository;
import com.learning.ThymeleafSpringSecurity.entity.User;
import com.learning.ThymeleafSpringSecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    //mapping for list of users
    @GetMapping("/list")
    public String listUsers(Model model) {

        //get Users from db
        List<User> users = userService.findAll();

        //add to the spring model
        model.addAttribute("users", users);

        return "users/list-users";
    }

    //the form for saving/adding an user
    @GetMapping("/showFormForAdd") // addUser // showFormForAdd
    public String showFormForAdd(Model model) {

        //create model attribute to bind form data
        User user = new User();

        model.addAttribute("user", user); //Thymeleaf template access this data to bind the form data

        return "users/user-add-form";
    }

    //updating user information by user id
    //another way of updating user info
    @PostMapping("/showFormForUpdate") // updateUser //showFormForUpdate
    public String showFormForUpdate(@RequestParam("userId") Long id,
                             Model model) {
        //getting the uer form by user id
        User user = userService.findById(id);

        //setting the user as a model attribute to pre-populate the form
        model.addAttribute("user", user);

        //sending  to the form
        return "users/user-update-form";
    }

    //adding a new user by ADMIN
    @PostMapping("/add")
    public String addUser(@ModelAttribute("user") User user) {


        //assigning the default role to the new user while adding by ADMIN
        user.setRoles(Arrays.asList(roleRepository.findRoleByName("USER")));

        //encodes the
         user.setPassword(passwordEncoder.encode(user.getPassword()));

        //save the user
        userService.save(user);

        //redirecting to prevent duplicate submission
        return "redirect:/users/list";
    }

    // upon updating the user by ADMIN
    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") User user) {


        //save the user
        userService.save(user);

        //redirecting to prevent duplicate submission
        return "redirect:/users/list";
    }

    //deleting an user
    @PostMapping("/delete")
    public String delete(@RequestParam("userId") Long id) {

        // deleting the foreign key first
        // no need to use this since the issue has been solved by the change in cascade
        //user.getRoles().remove(0);

        //deleting an user
        userService.deleteById(id);

        //redirecting to prevent duplicate submissions
        return "redirect:/users/list";
    }

//searches for an user by name
    @GetMapping("/search")
    public String search(@RequestParam("userName") String name,
                         Model model) {

        // search the user
        List<User> users = userService.searchBy(name);

        // add to the spring model
        model.addAttribute("users", users);

        // send to /users/list
        return "/users/list-users";
    }

}
