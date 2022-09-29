package com.metric.eticaret.user.controller;


import com.metric.eticaret.exception.domain.NotFoundException;
import com.metric.eticaret.user.model.Role;
import com.metric.eticaret.user.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/user/role")
public class RoleController {

    private final RoleService roleService;

    @PostMapping("/save")
    public ResponseEntity<Role> save(@RequestBody Role role) throws NotFoundException {
        return new ResponseEntity<>(roleService.save(role), HttpStatus.OK);
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> retrieveAllRoles(){
        return new ResponseEntity<>(roleService.retrieveAllRoles(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable("id") Integer id) throws NotFoundException {
        return new ResponseEntity<>(roleService.getRoleById(id),HttpStatus.OK);
    }
}
