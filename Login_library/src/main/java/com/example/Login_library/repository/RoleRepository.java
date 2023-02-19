package com.example.Login_library.repository;

import com.example.Login_library.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role ,Long> {

    Optional<Role> findByName(String Name);
}
