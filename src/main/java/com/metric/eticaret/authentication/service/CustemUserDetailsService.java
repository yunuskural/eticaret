package com.metric.eticaret.authentication.service;

import com.metric.eticaret.user.model.Authority;
import com.metric.eticaret.user.model.Role;
import com.metric.eticaret.user.model.User;
import com.metric.eticaret.user.repository.RoleRepository;
import com.metric.eticaret.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@RequiredArgsConstructor
@Service("userDetailService")
@Transactional
public class CustemUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            return new org.springframework.security.core.userdetails.User(
                    " ", " ", true, true, true, true,
                    getAuthorities(Arrays.asList(
                            roleRepository.findByRoleName("ROLE_USER"))));
        }else {
            user.setLastLoginDate(new Date().getTime());
            userRepository.save(user);
            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(), user.getPassword(), user.isActive(), true, true,
                    true, getAuthorities(user.getRoles()));
        }

    }

    public Collection<? extends GrantedAuthority> getAuthorities(List<Role> roles) {
        return getGrantedAuthorities(getPrivileges(roles));
    }

    public List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }

    private List<String> getPrivileges(List<Role> roles) {

        List<String> privileges = new ArrayList<>();
        List<Authority> authorities = new ArrayList<>();
        for (Role role : roles) {
            privileges.add(role.getRoleName());
            authorities.addAll(role.getAuthorities());
        }
        for (Authority item : authorities) {
            privileges.add(item.getName());
        }
        return privileges;
    }




}
