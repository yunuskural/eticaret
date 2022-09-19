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
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailServiceImpl emailService;

    @Transactional
    @Override
    public User save(User newUser) throws NotFoundException, ExistException {
        validateUser(newUser);
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
        user.setActive(Boolean.TRUE);
        user.setNotLocked(Boolean.TRUE);
        userRepository.save(user);
        return user;

        //emailService.sendEmail(user.getEmail(),"New Account","New Account has been created successfully");
    }

    public User validateUser(User newUser) throws ExistException {
        User userByUsername = userRepository.findByUsername(newUser.getUsername());
        User userByEmail = userRepository.findByEmail(newUser.getEmail());
        if (userByUsername != null && !userByUsername.getId().equals(newUser.getId())) {
            throw new ExistException("Username already exists");
        }
        if (userByEmail != null && !userByEmail.getId().equals(newUser.getId())) {
            throw new ExistException("Email already exists");
        }
        return null;
    }


    @Override
    public User getUserById(String username) throws NotFoundException {
        User user= userRepository.findByUsername(username);
        if (user==null){
            throw new NotFoundException("User not found");
        }
        return user;
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}
