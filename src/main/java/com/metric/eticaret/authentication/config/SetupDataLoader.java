package com.metric.eticaret.authentication.config;

import com.metric.eticaret.user.model.authority.Authority;
import com.metric.eticaret.user.model.role.Role;
import com.metric.eticaret.user.repository.AuthorityRepository;
import com.metric.eticaret.user.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SetupDataLoader implements
        ApplicationListener<ContextRefreshedEvent> {

    private final RoleRepository roleRepository;
    private final AuthorityRepository authorityRepository;
    boolean alreadySetup = false;

    public static final String READ_AUTHORITY = "READ_AUTHORITY";
    public static final String WRITE_AUTHORITY = "WRITE_AUTHORITY";
    public static final String UPDATE_AUTHORITY = "UPDATE_AUTHORITY";
    public static final String DELETE_AUTHORITY = "DELETE_AUTHORITY";
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_HR = "ROLE_HR";
    public static final String ROLE_MANAGER = "ROLE_MANAGER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_SUPER_ADMIN = "ROLE_SUPER_ADMIN";


    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup) return;

        Authority readAuthority = createPrivilegeIfNotFound(READ_AUTHORITY);
        Authority writeAuthority = createPrivilegeIfNotFound(WRITE_AUTHORITY);
        Authority updateAuthority = createPrivilegeIfNotFound(UPDATE_AUTHORITY);
        Authority deleteAuthority = createPrivilegeIfNotFound(DELETE_AUTHORITY);

        List<Authority> userAuthorities = Arrays.asList(readAuthority, writeAuthority);
        List<Authority> hrAuthorities = Arrays.asList(readAuthority, writeAuthority);
        List<Authority> managerAuthorities = Arrays.asList(readAuthority, writeAuthority, updateAuthority);
        List<Authority> adminAuthorities = Arrays.asList(readAuthority, writeAuthority, updateAuthority);
        List<Authority> superAdminAuthorities = Arrays.asList(readAuthority, writeAuthority, updateAuthority, deleteAuthority);

        createRoleIfNotFound(ROLE_USER, userAuthorities);
        createRoleIfNotFound(ROLE_HR, hrAuthorities);
        createRoleIfNotFound(ROLE_MANAGER, managerAuthorities);
        createRoleIfNotFound(ROLE_ADMIN, adminAuthorities);
        createRoleIfNotFound(ROLE_SUPER_ADMIN, superAdminAuthorities);

        alreadySetup = true;
    }

    @Transactional
    Authority createPrivilegeIfNotFound(String name) {

        Authority authority = authorityRepository.findByName(name);
        if (authority == null) {
            authority = new Authority(name);
            authorityRepository.save(authority);
        }
        return authority;
    }

    @Transactional
    Role createRoleIfNotFound(String roleName, List<Authority> authorities) {
        Role role = roleRepository.findByRoleName(roleName);
        if (role == null) {
            role = new Role();
            role.setRoleName(roleName);
            role.setAuthorities(authorities);
            roleRepository.save(role);
        }
        return role;
    }

}
