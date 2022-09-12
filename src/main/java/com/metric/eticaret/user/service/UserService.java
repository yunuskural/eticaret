package com.metric.eticaret.user.service;

import com.metric.eticaret.exception.domain.UserNotFoundException;
import com.metric.eticaret.exception.domain.UsernameExistException;
import com.metric.eticaret.exception.domain.UsernameNotFoundException;
import com.metric.eticaret.user.model.User;

public interface UserService {

    User save(User user) throws UsernameNotFoundException, UsernameExistException;
    User getUser(Long id) throws UserNotFoundException;
    void deleteUser(Long id) throws UserNotFoundException;
}
