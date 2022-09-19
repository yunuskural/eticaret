package com.metric.eticaret.authentication.controller;

import com.metric.eticaret.authentication.model.LoginRequest;
import com.metric.eticaret.authentication.model.LoginResponse;
import com.metric.eticaret.authentication.service.AuthenticationService;
import com.metric.eticaret.exception.ExceptionHandling;
import com.metric.eticaret.exception.domain.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationController extends ExceptionHandling {

    private final AuthenticationService authenticationService;

    @PostMapping("/authenticate")
    public ResponseEntity<LoginResponse> authenticate(@Valid @RequestBody LoginRequest loginRequest) throws NotFoundException {
        String token = authenticationService.authenticate(loginRequest);
        return new ResponseEntity<>(new LoginResponse(token), HttpStatus.OK);
    }


}
