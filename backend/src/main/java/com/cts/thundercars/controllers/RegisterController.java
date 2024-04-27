package com.cts.thundercars.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.thundercars.entity.User;
import com.cts.thundercars.exceptions.AlreadyExistsException;
import com.cts.thundercars.exceptions.NotFoundException;
import com.cts.thundercars.repository.UserRepository;
import com.cts.thundercars.security.AuthenticationResponse;
import com.cts.thundercars.security.JwtUtil;
import com.cts.thundercars.security.MyUserDetailsService;
import com.cts.thundercars.services.service.UserServices;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/register")
public class RegisterController {
 
    @Autowired
    private UserRepository usersDao;
 
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private UserServices userService;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private MyUserDetailsService myUserDetailsService;
 
    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody User user,HttpServletResponse res) throws AlreadyExistsException, NotFoundException {
    	Boolean isExists = usersDao.existsByEmail(user.getEmail());
    	
        if (isExists) {
            throw new AlreadyExistsException("User Already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
        UserDetails userDetail= myUserDetailsService.loadUserByUsername(user.getEmail());
        String jwt = jwtUtil.generateToken(userDetail);
        res.addHeader("jwt", jwt);
        return ResponseEntity.ok(new AuthenticationResponse("Registered successfully"));
    }
}