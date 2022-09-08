package com.metric.eticaret.user.controller;


import com.metric.eticaret.authentication.model.HttpResponse;
import com.metric.eticaret.exception.domain.UsernameNotFoundException;
import com.metric.eticaret.user.model.User;
import com.metric.eticaret.user.repository.UserRepository;
import com.metric.eticaret.user.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/user")
public class UserController {

    private final UserServiceImpl userServiceImpl;
    private final UserRepository userRepository;



    @PostMapping("/save")
    public ResponseEntity<HttpResponse> save(@RequestBody User newUser) throws UsernameNotFoundException {
        User user = userServiceImpl.save(newUser);
        HttpStatus httpStatus = HttpStatus.OK;
        HttpResponse httpResponse = new HttpResponse(httpStatus.value(), httpStatus,
                httpStatus.getReasonPhrase().toUpperCase(Locale.ROOT), "Successfull", Collections.singletonList(user));
        return new ResponseEntity<>(httpResponse, httpStatus);
    }

    @GetMapping("/users")
    public ResponseEntity<HttpResponse> retrieveAllUser() {
        List<User> users = userRepository.findAll();
        HttpStatus httpStatus = HttpStatus.OK;
        HttpResponse httpResponse = new HttpResponse(httpStatus.value(), httpStatus,
                httpStatus.getReasonPhrase().toUpperCase(Locale.ROOT), "Successfull", Collections.singletonList(users));
        return new ResponseEntity<>(httpResponse, httpStatus);
    }

    @DeleteMapping("/user/{id}")
    public void deleteUserById(@PathVariable("id") Long id) {
        userRepository.deleteById(id);
    }

    @GetMapping("/user/{id}")
    public void getUserById(@PathVariable("id") Long id) {
        userRepository.findById(id);
    }
}
