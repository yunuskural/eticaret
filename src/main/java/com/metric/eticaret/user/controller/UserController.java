package com.metric.eticaret.user.controller;


import com.metric.eticaret.authentication.config.HttpResponseService;
import com.metric.eticaret.authentication.model.HttpResponse;
import com.metric.eticaret.exception.domain.ExistException;
import com.metric.eticaret.exception.domain.NotFoundException;
import com.metric.eticaret.user.model.user.UserDTO;
import com.metric.eticaret.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final HttpResponseService httpResponseService;


    @PostMapping("/save")
    public ResponseEntity<HttpResponse> save(@RequestBody UserDTO userDTO) throws NotFoundException, ExistException {
        return httpResponseService.response(userService.save(userDTO), String.format("User %s Successfully created. Now you can login..", userDTO.getUsername()), HttpStatus.CREATED);
    }

    @GetMapping("/users")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<List<UserDTO>> retrieveAllUsers() {
        return new ResponseEntity<>(userService.retrieveAllUsers(), OK);

    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable("id") Long id) throws NotFoundException {
        userService.deleteUserById(id);
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable("username") String username) throws NotFoundException {
        return new ResponseEntity<>(userService.findUserByUsername(username), OK);
    }

    @GetMapping("user-id/{id}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable("id") Long id) throws NotFoundException {
        return new ResponseEntity<>(userService.findUserById(id), OK);
    }
}
