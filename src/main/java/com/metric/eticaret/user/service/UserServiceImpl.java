package com.metric.eticaret.user.service;


import com.metric.eticaret.authentication.config.EmailServiceImpl;
import com.metric.eticaret.exception.domain.*;
import com.metric.eticaret.user.model.Role;
import com.metric.eticaret.user.model.User;
import com.metric.eticaret.user.repository.RoleRepository;
import com.metric.eticaret.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailServiceImpl emailService;

    @Transactional
    @Override
    public User save(User newUser) throws NotFoundException{
        User user;
        String password = newUser.getPassword();
        Long joinDate = new Date().getTime();
        List<Role> roles = newUser.getRoles();
        if (newUser.getId() != null && newUser != null) {
            user = userRepository.findById(newUser.getId()).orElseThrow(() -> new NotFoundException("User not found"));
            password = user.getPassword();
            joinDate = user.getJoinDate();
            roles = user.getRoles();
        }
        user = newUser;
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setJoinDate(joinDate);
        if (user.getRoles() == null && roles == null) {
            user.setRoles(Arrays.asList(roleRepository.findByRoleName("ROLE_USER")));
        } else {
            List<Role> newRoles = new ArrayList<>();
            user.getRoles().forEach(role -> {
                newRoles.add(roleRepository.findByRoleName(role.getRoleName()));
            });
            user.setRoles(newRoles);
        }
        user.setActive(true);
        user.setNotLocked(true);
        userRepository.save(user);
        return user;

        //emailService.sendEmail(user.getEmail(),"New Account","New Account has been created successfully");
    }


    @Override
    public User getUser(Long id) throws NotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}
