package com.aluraChallenge.forohub.domain.user;
import com.aluraChallenge.forohub.domain.rol.Rol;
import com.aluraChallenge.forohub.domain.topico.Topico;

public record UserDTO(Long Id, String username, String email, Rol rol, Integer numTopicosCreated, String[] topicosCreated, Integer numComments) {

    public UserDTO(User user) {
        this(user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRol(),
                user.getNumTopicosCreated(),
                user.getTopicos().stream().map(Topico::getTitle).toList().toArray(new String[0]),
                user.getNumComments());
    }

}
