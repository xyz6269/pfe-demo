package com.example.Login_library.controller;

import com.example.Login_library.DTO.AuthenticationRequest;
import com.example.Login_library.DTO.AuthenticationResponse;
import com.example.Login_library.DTO.RegisterRequest;
import com.example.Login_library.entity.User;
import com.example.Login_library.repository.UserRepository;
import com.example.Login_library.service.AuthenticationService;
import com.example.Login_library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
            return new ResponseEntity<AuthenticationResponse>(HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequest request){

        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @GetMapping()
    public List<User> getEveryUsers(User user) {
        return userService.getallUsers();
    }

    @GetMapping("{id}")
    public User getUsersById(@PathVariable("id") long id) {
        return userService.getUserById(id);
    }

    @DeleteMapping("{id}")
    public void getDeleteById(@PathVariable("id") long id) {
         userService.deleteUser(userRepository.findById(id).orElseThrow(()-> new RuntimeException("no user with this id") ));
    }


}
