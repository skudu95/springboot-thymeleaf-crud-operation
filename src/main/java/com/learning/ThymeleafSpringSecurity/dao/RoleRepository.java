package com.learning.ThymeleafSpringSecurity.dao;

import com.learning.ThymeleafSpringSecurity.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    public Role findRoleByName(String roleName);
}
