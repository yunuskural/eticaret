package com.metric.eticaret.user.controller;


import com.metric.eticaret.authentication.config.HttpResponseService;
import com.metric.eticaret.authentication.model.HttpResponse;
import com.metric.eticaret.exception.domain.ExistException;
import com.metric.eticaret.exception.domain.NotFoundException;
import com.metric.eticaret.user.model.User;
import com.metric.eticaret.user.repository.UserRepository;
import com.metric.eticaret.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final HttpResponseService httpResponseService;


    @PostMapping("/save")
    public ResponseEntity<HttpResponse> save(@RequestBody User newUser) throws NotFoundException, ExistException {
        User user = userService.save(newUser);
        return httpResponseService.response(user, String.format("User %s Successfully created", newUser.getUsername()), HttpStatus.CREATED);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> retrieveAllUser() {
        return new ResponseEntity<>(userRepository.findAll(),HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    //@PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<HttpResponse> deleteUserById(@PathVariable("id") Long id) throws NotFoundException {
        userService.deleteUserById(id);
        return httpResponseService.response(null, "User deleted successfully", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HttpResponse> getUserByUsername(@PathVariable("id") Long id) throws NotFoundException {
        User user = userService.getUserById(id);
        return httpResponseService.response(user, "Successfull", HttpStatus.OK);
    }
}
