package com.learning.ThymeleafSpringSecurity.service;

import com.learning.ThymeleafSpringSecurity.entity.Role;
import com.learning.ThymeleafSpringSecurity.model.MyUserDetails;
import com.learning.ThymeleafSpringSecurity.dao.RoleRepository;
import com.learning.ThymeleafSpringSecurity.dao.UserRepository;
import com.learning.ThymeleafSpringSecurity.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User findByUserName(String userName) {

        //checking the database if the user already exists
        return userRepository.findByUserName(userName);
    }

   @Override
   @Transactional
    public void save(MyUserDetails myUserDetails) {
        User user = new User();

        //assigning user details to the user object
        user.setUserName(myUserDetails.getUserName());

        //encrypting the password
        user.setPassword(passwordEncoder.encode(myUserDetails.getPassword()));
        user.setFirstName(myUserDetails.getFirstName());
        user.setLastName(myUserDetails.getLastName());
        user.setEmail(myUserDetails.getEmail());

        //assigning a default role "USER" to the user while registration
       user.setRoles(Arrays.asList(roleRepository.findRoleByName("USER")));
       // user.setRoles(Collections.singletonList(roleRepository.findRoleByName("USER")));

        userRepository.save(user);

    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(userName);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private List<? extends GrantedAuthority> mapRolesToAuthorities(List<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }


    @Override
    public List<User> findAll() {
        //   return userRepository.findAll();
        return userRepository.findAllByOrderByFirstNameAsc();
    }

    @Override
    public User findById(Long id) {
        Optional<User> result = userRepository.findById(id);

        User user = null;

        if (result.isPresent()){
            user = result.get();
        }
        else{
            //user not found
            throw new RuntimeException(("User id not found :" + id));
        }
        return user;
    }


    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);

    }

    @Override
    public List<User> searchBy(String name) {
        List<User> results = null;

        if(name != null &&(name.trim().length()>0)){
            results = userRepository.findByFirstNameContainsOrLastNameContainsAllIgnoreCase(name, name);
        }else {
            results = findAll();
        }
        return results;
    }
}
