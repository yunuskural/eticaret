package com.metric.eticaret.user.service;


import com.metric.eticaret.exception.domain.UsernameNotFoundException;
import com.metric.eticaret.user.model.User;
import com.metric.eticaret.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{


    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    @Override
    public User save(User user) throws UsernameNotFoundException {

        if (user.getId() != null && user != null) {
            userRepository.findById(user.getId()).orElseThrow(() -> new UsernameNotFoundException("user not found"));
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setJoinDate(new Date().getTime());
       // user.setRoles(role);
        user.setActive(true);
        user.setNotLocked(true);
        user.setProfileImageUrl(ServletUriComponentsBuilder.fromCurrentContextPath().path("user/image/profile/temp").toUriString());
        userRepository.save(user);
        return user;
    }

}
