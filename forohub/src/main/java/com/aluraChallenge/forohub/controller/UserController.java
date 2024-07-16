package com.aluraChallenge.forohub.controller;
import com.aluraChallenge.forohub.domain.user.UserAddDTO;
import com.aluraChallenge.forohub.domain.user.UserDTO;
import com.aluraChallenge.forohub.domain.user.UserUpdatePasswordDTO;
import com.aluraChallenge.forohub.infra.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;



@RestController
@RequestMapping("/con/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener la información del usuario con el Id")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        var user = userService.getUser(id);
        if (user == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(user);
    }

    @PostMapping
    @Operation(summary = "Registrar usuario")
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid UserAddDTO userAddDTO) {
        var user = userService.createUser(userAddDTO);
        var uri = UriComponentsBuilder.fromPath("/users/{id}").buildAndExpand(user.Id()).toUri();
        return ResponseEntity.created(uri).body(user);
    }

    @PutMapping("/changePassword")
    @Operation(summary = "Cambiar la contraseña")
    public ResponseEntity<UserDTO> changePassword(@RequestBody @Valid UserUpdatePasswordDTO userUpdatePasswordDTO) {
        var user = userService.updatePassword(userUpdatePasswordDTO);
        if (user == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un usuario bucandolo por su id")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}

