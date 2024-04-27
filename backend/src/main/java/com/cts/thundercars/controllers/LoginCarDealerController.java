package com.cts.thundercars.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.thundercars.security.AuthenticationRequest;
import com.cts.thundercars.security.AuthenticationResponse;
import com.cts.thundercars.security.JwtUtil;
import com.cts.thundercars.security.MyUserDetailsService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/login/carDealer")
public class LoginCarDealerController {

    @Autowired
    private AuthenticationManager authenticationManager;
 
    @Autowired
    private JwtUtil jwtUtil;
 
    @Autowired
    private MyUserDetailsService myUserDetailsService;
    
 
    @PostMapping
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest,HttpServletResponse res) throws Exception {
        
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Incorrect username or password");
        }
 
        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        
        
        final String jwt = jwtUtil.generateToken(userDetails);
        
        res.setHeader("jwt", jwt);
 
        return ResponseEntity.ok(new AuthenticationResponse("logged in successfully"));
    }
    
//    @GetMapping("/load/carDealer")
//    public ResponseEntity<?> loadUser(HttpServletRequest req){
//        String authHeader = req.getHeader("Authorization");
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            String jwt = authHeader.substring(7);
//            String username = jwtUtil.extractUsername(jwt);
//            UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);
//            if (username != null && jwtUtil.validateToken(jwt, userDetails)) {
//                Optional<CarDealer> optionalCarDealer = carDealerRepository.findByEmail(username);
//                if (optionalCarDealer.isPresent()) {
//                    return ResponseEntity.ok(optionalCarDealer.get());
//                } else {
//                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CarDealer not found");
//                }
//            }
//        }
//        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid JWT token");
//    }

}