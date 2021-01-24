package com.learning.ThymeleafSpringSecurity.service;

import com.learning.ThymeleafSpringSecurity.entity.User;
import com.learning.ThymeleafSpringSecurity.model.MyUserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    public User findByUserName(String userName);

    // mistakenly called it in the UserRepository
   // public void save(User user);

    // for the new user registration
    public void save (MyUserDetails myUserDetails);


    public List<User> findAll();

    public User findById(Long id);

    // for updating the registered user
    public void save(User user);

    public void deleteById(Long id);

    public List<User> searchBy(String name);

}
