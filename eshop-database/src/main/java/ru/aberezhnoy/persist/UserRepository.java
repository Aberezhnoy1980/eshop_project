package ru.aberezhnoy.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aberezhnoy.persist.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
