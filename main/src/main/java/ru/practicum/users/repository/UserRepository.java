package ru.practicum.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.users.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsUserByEmail(String email);

    User getUserById(int userId);
}
