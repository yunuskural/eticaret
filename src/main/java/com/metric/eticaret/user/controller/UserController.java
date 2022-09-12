package com.metric.eticaret.user.controller;


import com.metric.eticaret.authentication.model.HttpResponse;
import com.metric.eticaret.authentication.model.HttpResponseService;
import com.metric.eticaret.exception.domain.UserNotFoundException;
import com.metric.eticaret.exception.domain.UsernameExistException;
import com.metric.eticaret.exception.domain.UsernameNotFoundException;
import com.metric.eticaret.user.model.User;
import com.metric.eticaret.user.repository.UserRepository;
import com.metric.eticaret.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final HttpResponseService httpResponseService;


    @PostMapping("/save")
    public ResponseEntity<HttpResponse> save(@RequestBody User newUser) throws UsernameNotFoundException, UsernameExistException {
        User user = userService.save(newUser);
        return httpResponseService.response(user, "Successfully created", HttpStatus.CREATED);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> retrieveAllUser() {
        return new ResponseEntity<>(userRepository.findAll(),HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<HttpResponse> deleteUserById(@PathVariable("id") Long id) throws UserNotFoundException {
        userService.deleteUser(id);
        return httpResponseService.response(null,"User deleted successfully", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HttpResponse> getUserById(@PathVariable("id") Long id) throws UserNotFoundException {
        User user = userService.getUser(id);
        return httpResponseService.response(user, "Successfull", HttpStatus.OK);
    }
}
