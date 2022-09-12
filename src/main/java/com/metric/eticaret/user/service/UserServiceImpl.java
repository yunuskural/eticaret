package com.metric.eticaret.user.service;


import com.metric.eticaret.exception.domain.UserNotFoundException;
import com.metric.eticaret.exception.domain.UsernameExistException;
import com.metric.eticaret.exception.domain.UsernameNotFoundException;
import com.metric.eticaret.user.model.Role;
import com.metric.eticaret.user.model.User;
import com.metric.eticaret.user.repository.RoleRepository;
import com.metric.eticaret.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    @Override
    public User save(User newUser) throws UsernameNotFoundException, UsernameExistException {
        User user;
        String password = newUser.getPassword();
        Long joinDate = new Date().getTime();
        List<Role> roles = newUser.getRoles();
        if (newUser.getId() != null && newUser != null) {
            user = userRepository.findById(newUser.getId()).orElseThrow(() -> new UsernameNotFoundException("user not found"));
            password = user.getPassword();
            joinDate = user.getJoinDate();
            roles = user.getRoles();
        }
        user = newUser;
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setJoinDate(joinDate);
        if (user.getRoles() == null && roles==null) {
            user.setRoles(Arrays.asList(roleRepository.findByRoleName("ROLE_USER")));
        }else {
            user.setRoles(newUser.getRoles());
        }
        user.setActive(true);
        user.setNotLocked(true);
        userRepository.save(user);
        return user;
    }

    @Override
    public User getUser(Long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}
