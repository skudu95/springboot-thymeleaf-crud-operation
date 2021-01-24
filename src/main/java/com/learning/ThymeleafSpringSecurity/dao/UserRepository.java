package com.learning.ThymeleafSpringSecurity.dao;

import com.learning.ThymeleafSpringSecurity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public User findByUserName(String userName);

    //adding a method to sort by first name
    public List<User> findAllByOrderByFirstNameAsc();

    //search by name
    List<User> findByFirstNameContainsOrLastNameContainsAllIgnoreCase(String name, String lname);


}
