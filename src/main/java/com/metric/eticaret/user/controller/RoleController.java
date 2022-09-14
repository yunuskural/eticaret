package com.metric.eticaret.user.controller;


import com.metric.eticaret.exception.domain.NotFoundException;
import com.metric.eticaret.user.model.Role;
import com.metric.eticaret.user.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/role")
public class RoleController {

    private final RoleService roleService;

    @PostMapping("/save")
    public ResponseEntity<Role> save(@RequestBody Role role) throws NotFoundException {
        return new ResponseEntity<>(roleService.save(role), HttpStatus.OK);
    }

  /*  @PostMapping("/role/{id}/authority")
    public ResponseEntity<Role> createAuthorityForRole(@PathVariable("id") Integer id) throws RoleNotFoundException {
        return new ResponseEntity<>(roleService.createAuthorityForRole(id),HttpStatus.OK);
    }*/
}
