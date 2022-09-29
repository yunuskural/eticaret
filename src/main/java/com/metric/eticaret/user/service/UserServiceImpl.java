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

import static com.metric.eticaret.authentication.config.SetupDataLoader.ROLE_USER;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    @Override
    public User save(User newUser) throws NotFoundException, ExistException {
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
        validateUser(newUser);
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
    public User getUserById(Long id) throws NotFoundException {
        User user = userRepository.findById(id).orElseThrow(()-> new NotFoundException("User not found"));
           User.UserBuilder userBuilder = User.builder()
                   .id(user.getId())
                   .username(user.getUsername())
                   .address(user.getAddress())
                   .email(user.getEmail())
                   .name(user.getName())
                   .isActive(user.isActive())
                   .isNotLocked(user.isNotLocked())
                   .roles(user.getRoles());
           return userBuilder.build();
    }

    @Override
    public void deleteUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.getRoles().clear();
            userRepository.deleteById(id);
        }
    }

}
