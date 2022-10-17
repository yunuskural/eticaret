package com.metric.eticaret.user.service;

import com.metric.eticaret.exception.domain.ExistException;
import com.metric.eticaret.exception.domain.NotFoundException;
import com.metric.eticaret.user.model.user.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO save(UserDTO userDTO) throws NotFoundException, ExistException;

    UserDTO findUserByUsername(String username) throws NotFoundException;

    UserDTO findUserById(Long id) throws NotFoundException;

    void deleteUserById(Long id) throws NotFoundException;

    List<UserDTO> retrieveAllUsers();
}
