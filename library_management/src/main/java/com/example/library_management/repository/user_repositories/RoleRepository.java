package com.example.library_management.repository.user_repositories;


import com.example.library_management.entity.user_entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role ,Long> {
    Optional<Role> findByName(String Name);
}
