package com.metric.eticaret.user.service;

import com.metric.eticaret.exception.domain.NotFoundException;
import com.metric.eticaret.user.model.role.Role;
import com.metric.eticaret.user.model.role.RoleDTO;

import java.util.List;

public interface RoleService {

    Role save(Role role) throws NotFoundException;

    List<RoleDTO> retrieveAllRoles();

    RoleDTO getRoleById(Integer id) throws NotFoundException;

    RoleDTO getRoleByRoleName(String roleName) throws NotFoundException;
}
