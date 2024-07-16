package com.aluraChallenge.forohub.infra.service;


import com.aluraChallenge.forohub.domain.user.UserAddDTO;
import com.aluraChallenge.forohub.domain.user.UserDTO;
import com.aluraChallenge.forohub.domain.user.UserUpdatePasswordDTO;

public interface UserService {
    UserDTO getUser(Long id);
    UserDTO createUser(UserAddDTO userAddDTO);
    UserDTO updatePassword(UserUpdatePasswordDTO userUpdatePasswordDTO);
    void deleteUser(Long id);
}
