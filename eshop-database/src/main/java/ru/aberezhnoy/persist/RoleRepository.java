package ru.aberezhnoy.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aberezhnoy.persist.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
