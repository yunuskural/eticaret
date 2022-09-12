package com.metric.eticaret.authentication.controller;

import com.metric.eticaret.authentication.config.JwtTokenUtil;
import com.metric.eticaret.authentication.model.LoginRequest;
import com.metric.eticaret.authentication.model.LoginResponse;
import com.metric.eticaret.authentication.service.CustemUserDetailsService;
import com.metric.eticaret.exception.ExceptionHandling;
import com.metric.eticaret.exception.domain.IncorrectCredentialException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController extends ExceptionHandling {

    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final CustemUserDetailsService custemUserDetailsService;


    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/authenticate")
    public ResponseEntity<LoginResponse> authenticate(@Valid @RequestBody LoginRequest loginRequest) throws IncorrectCredentialException {
    /*    try {
            authenticationManager.
                    authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new IncorrectCredentialException("username or password incorrect");
        }*/
        String token;
        UserDetails userDetails = custemUserDetailsService.loadUserByUsername(loginRequest.getUsername());
        if (userDetails.getUsername()==null){
            throw new IncorrectCredentialException("Username/password incorrect. Please try again");
        }
        token = jwtTokenUtil.generateToken(userDetails);

        return new ResponseEntity<>(new LoginResponse(token), HttpStatus.OK);
    }




}
