package com.example.library_management.service.user_service;


import com.example.library_management.entity.user_entities.Role;
import com.example.library_management.entity.user_entities.User;
import com.example.library_management.repository.user_repositories.RoleRepository;
import com.example.library_management.repository.user_repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final RoleRepository roleRepository;

    public List<User> getallUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(User user) {

        userRepository.delete(user);
    }

    public User getUserById(long id)  {
        return userRepository.
                findById(id).
                orElseThrow(
                        ()-> new RuntimeException("user not found"));
    }

    public User getUserByEmail(String email) {
        return userRepository.
                findByEmail(email).
                orElseThrow(
                        ()-> new RuntimeException("user not found"));
    }

    public void promoteUser(User user){
        Role role = roleRepository.findByName("Admin")
                .orElseThrow(
                        ()->new RuntimeException()
                );
        var list = user.getRoles();
        list.add(role);
        user.setRoles(list);
    }




}
