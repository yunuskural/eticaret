package com.metric.eticaret.user.service;

import com.metric.eticaret.exception.domain.NotFoundException;
import com.metric.eticaret.user.model.Role;

public interface RoleService {

    Role save(Role role) throws NotFoundException;

}
