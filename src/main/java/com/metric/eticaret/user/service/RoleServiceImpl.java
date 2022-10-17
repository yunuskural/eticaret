package com.metric.eticaret.user.service;


import com.metric.eticaret.exception.domain.NotFoundException;
import com.metric.eticaret.user.model.role.Role;
import com.metric.eticaret.user.model.role.RoleDTO;
import com.metric.eticaret.user.model.role.RoleMapper;
import com.metric.eticaret.user.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Transactional
    @Override
    public Role save(Role newRole) throws NotFoundException {
        if (newRole.getId() != null && newRole != null) {
            roleRepository.findById(newRole.getId()).orElseThrow(() -> new NotFoundException("Role is not found"));
        }
        return roleRepository.save(newRole);
    }

    @Override
    public List<RoleDTO> retrieveAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream().map(RoleMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }

    @Override
    public RoleDTO getRoleById(Integer id) throws NotFoundException {
        Role role = roleRepository.findById(id).orElseThrow(() -> new NotFoundException("Role not found"));
        return RoleMapper.INSTANCE.toDTO(role);
    }

    @Override
    public RoleDTO getRoleByRoleName(String roleName) throws NotFoundException {
        Role role = roleRepository.findByRoleName(roleName);
        if (role != null) {
            return RoleMapper.INSTANCE.toDTO(role);
        } else {
            throw new NotFoundException("Role not found");
        }
    }
}
