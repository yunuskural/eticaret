package com.metric.eticaret.user.service;


import com.metric.eticaret.exception.domain.NotFoundException;
import com.metric.eticaret.user.model.Role;
import com.metric.eticaret.user.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Transactional
    @Override
    public Role save(Role newRole) throws NotFoundException {
        if(newRole.getId() !=null && newRole != null){
            roleRepository.findById(newRole.getId()).orElseThrow(() -> new NotFoundException("Role is not found"));
        }
        return roleRepository.save(newRole);
    }
}
