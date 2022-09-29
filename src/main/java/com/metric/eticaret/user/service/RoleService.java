package com.metric.eticaret.user.service;

import com.metric.eticaret.exception.domain.NotFoundException;
import com.metric.eticaret.user.model.Role;

import java.util.List;

public interface RoleService {

    Role save(Role role) throws NotFoundException;
    List<Role> retrieveAllRoles();

    Role getRoleById(Integer id) throws NotFoundException;
}
