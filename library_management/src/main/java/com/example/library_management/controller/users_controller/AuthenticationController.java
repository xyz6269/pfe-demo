package com.example.library_management.controller.users_controller;


import com.example.library_management.DTO.users_DTO.AuthenticationRequest;
import com.example.library_management.DTO.users_DTO.AuthenticationResponse;
import com.example.library_management.DTO.users_DTO.RegisterRequest;
import com.example.library_management.entity.user_entities.User;
import com.example.library_management.repository.user_repositories.UserRepository;
import com.example.library_management.service.user_service.AuthenticationService;
import com.example.library_management.service.user_service.UserService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.cdi.Eager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    @Autowired
    private final AuthenticationService authenticationService;
    @Autowired
    private final UserService userService;
    @Autowired
    private final UserRepository userRepository;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){

        if (userRepository.findByEmail(request.getEmail()).isPresent()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequest request){

        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @GetMapping()
    @RolesAllowed("Admin")
    public List<User> getEveryUsers(User user) {
        return userService.getallUsers();
    }

    @GetMapping("{id}")
    @RolesAllowed("Admin")
    public User getUsersById(@PathVariable("id") long id) {
        return userService.getUserById(id);
    }

    @DeleteMapping("{id}")
    @RolesAllowed("Admin")
    public void getDeleteById(@PathVariable("id") long id) {
         userService.deleteUser(userRepository.findById(id).orElseThrow(()-> new RuntimeException("no user with this id") ));
    }

    @GetMapping("/current_user")
    public User getCurrentUser(){
       return authenticationService.getCurrentUser();
    }


}
