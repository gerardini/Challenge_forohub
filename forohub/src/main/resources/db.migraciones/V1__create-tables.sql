CREATE TABLE IF NOT EXISTS users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL,
                       rol VARCHAR(50) NOT NULL,
                       token bigint NOT NULL;
);

CREATE TABLE IF NOT EXISTS cursos (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         categoria VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS topicos (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        title VARCHAR(255) NOT NULL,
                        content TEXT NOT NULL,
                        user_id BIGINT NOT NULL,
                        curso_id BIGINT NOT NULL,
                        num_responses INTEGER,
                        created_at TIMESTAMP NOT NULL,
                        FOREIGN KEY (user_id) REFERENCES users(id),
                        FOREIGN KEY (curso_id) REFERENCES cursos(id)
);

CREATE TABLE IF NOT EXISTS replies (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         message TEXT NOT NULL,
                         created_at TIMESTAMP NOT NULL,
                         replies_to INTEGER,
                         topico_id BIGINT NOT NULL,
                         user_id BIGINT NOT NULL,
                         FOREIGN KEY (topico_id) REFERENCES topicos(id),
                         FOREIGN KEY (user_id) REFERENCES users(id)
);