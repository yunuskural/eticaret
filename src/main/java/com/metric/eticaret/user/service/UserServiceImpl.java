package com.metric.eticaret.user.service;


import com.metric.eticaret.exception.domain.ExistException;
import com.metric.eticaret.exception.domain.NotFoundException;
import com.metric.eticaret.user.model.role.Role;
import com.metric.eticaret.user.model.user.User;
import com.metric.eticaret.user.model.user.UserDTO;
import com.metric.eticaret.user.model.user.UserMapper;
import com.metric.eticaret.user.repository.RoleRepository;
import com.metric.eticaret.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    @Override
    public UserDTO save(UserDTO userDTO) throws NotFoundException, ExistException {
        User user;
        if (userDTO.getId() != null && userDTO != null) {
            userRepository.findById(userDTO.getId()).orElseThrow(() -> new NotFoundException("User not found"));
            user = UserMapper.INSTANCE.toEntity(userDTO);
            validateUser(user);
            user.setRoles(userSetRoles(user));
            return UserMapper.INSTANCE.toDTO(userRepository.save(user));
        } else {
            user = UserMapper.INSTANCE.toEntity(userDTO);
            validateUser(user);
            user.setRoles(userSetRoles(user));
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setJoinDate(new Date().getTime());
            return UserMapper.INSTANCE.toDTO(userRepository.save(user));
        }
    }

    private Set<Role> userSetRoles(User user) {
        if (user.getRoles() == null) {
            user.setRoles(Collections.singleton(roleRepository.findByRoleName("ROLE_USER")));
        } /*else {
            Set<Role> newRoles = new HashSet<>();
            user.getRoles().forEach(role -> {
                newRoles.add(roleRepository.findByRoleName(role.getRoleName()));
            });
            user.setRoles(newRoles);
        }*/
        return user.getRoles();
    }

    public void validateUser(User user) throws ExistException, NotFoundException {
        User userByUsername = userRepository.findByUsername(user.getUsername());
        User userByEmail = userRepository.findByEmail(user.getEmail());
        if (userByUsername != null && !userByUsername.getId().equals(user.getId())) {
            throw new ExistException("Username already exists");
        }
        if (userByEmail != null && !userByEmail.getId().equals(user.getId())) {
            throw new ExistException("Email already exists");
        }

    }

    @Override
    public UserDTO findUserByUsername(String username) throws NotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new NotFoundException("User not found");
        }
        return UserMapper.INSTANCE.toDTO(user);
    }

    @Override
    public UserDTO findUserById(Long id) throws NotFoundException {
        return UserMapper.INSTANCE.toDTO(userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found")));
    }


    @Override
    public void deleteUserById(Long id) throws NotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        user.getRoles().clear();
        user.getOrders().clear();
        user.setShopCard(null);
        userRepository.save(user);
        userRepository.deleteById(user.getId());
    }

    @Override
    public List<UserDTO> retrieveAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(UserMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }

}
