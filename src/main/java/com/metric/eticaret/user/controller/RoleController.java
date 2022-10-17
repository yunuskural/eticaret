package com.metric.eticaret.user.controller;


import com.metric.eticaret.exception.domain.NotFoundException;
import com.metric.eticaret.user.model.role.Role;
import com.metric.eticaret.user.model.role.RoleDTO;
import com.metric.eticaret.user.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/user/role")
public class RoleController {

    private final RoleService roleService;

    @PostMapping("/save")
    public ResponseEntity<Role> save(@RequestBody Role role) throws NotFoundException {
        return new ResponseEntity<>(roleService.save(role), OK);
    }

    @GetMapping("/roles")
    public ResponseEntity<List<RoleDTO>> retrieveAllRoles(){
        return new ResponseEntity<>(roleService.retrieveAllRoles(), OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> getRoleById(@PathVariable("id") Integer id) throws NotFoundException {
        return new ResponseEntity<>(roleService.getRoleById(id), OK);
    }

    @GetMapping("role-name/{roleName}")
    public ResponseEntity<RoleDTO> getRoleByRoleName(@PathVariable("roleName") String roleName) throws NotFoundException {
        return new ResponseEntity<>(roleService.getRoleByRoleName(roleName), OK);
    }
}
