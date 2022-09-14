package com.metric.eticaret.user.service;

import com.metric.eticaret.exception.domain.ExistException;
import com.metric.eticaret.exception.domain.NotFoundException;
import com.metric.eticaret.user.model.User;

public interface UserService {

    User save(User user) throws NotFoundException, ExistException;
    User getUser(Long id) throws NotFoundException;
    void deleteUser(Long id) ;
}
