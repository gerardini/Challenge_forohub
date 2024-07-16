package com.aluraChallenge.forohub.infra.initializer;

import com.aluraChallenge.forohub.domain.user.UserAddDTO;
import com.aluraChallenge.forohub.domain.rol.Rol;
import com.aluraChallenge.forohub.domain.user.User;
import com.aluraChallenge.forohub.infra.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RolInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    @Autowired
    public RolInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        if (!userRepository.existsByUsername("admin")) {
            var userAddDTO = new UserAddDTO("admin", "admin@example.com", "admin", String.valueOf(Rol.ADMIN));

            userRepository.save(new User(userAddDTO));
        }
    }
}