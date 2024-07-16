package com.aluraChallenge.forohub.infra.service.imp;

import com.aluraChallenge.forohub.domain.user.UserAddDTO;
import com.aluraChallenge.forohub.domain.user.UserDTO;
import com.aluraChallenge.forohub.domain.user.UserUpdatePasswordDTO;
import com.aluraChallenge.forohub.domain.user.User;
import com.aluraChallenge.forohub.infra.repository.UserRepository;
import com.aluraChallenge.forohub.infra.service.UserService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO getUser(Long id) {
        var user = userRepository.findById(id);
        return user.map(UserDTO::new).orElse(null);
    }

    @Override
    @Transactional
    public UserDTO createUser(UserAddDTO userAddDTO) {
        if (userRepository.existsByUsername(userAddDTO.username()))
            throw new EntityExistsException("Un usuario con userId " + userAddDTO.username() + " ya existe");
        if (userRepository.existsByEmail(userAddDTO.email()))
            throw new EntityExistsException("Un usuario con email " + userAddDTO.email() + " ya existe");

        var user = new User(userAddDTO);
        userRepository.save(user);
        return new UserDTO(user);
    }

    @Override
    @Transactional
    public UserDTO updatePassword(UserUpdatePasswordDTO userUpdatePasswordDTO) {
        var optionalUser = userRepository.findById(userUpdatePasswordDTO.Id());
        if (optionalUser.isEmpty()) return null;

        var user = optionalUser.get();
        user.setPassword(userUpdatePasswordDTO.password());

        return new UserDTO(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        var user = userRepository.findById(id);
        if (user.isPresent())
            userRepository.delete(user.get());
        else
            throw new EntityNotFoundException("Un usuario con " + id + " no existe");
    }
}
