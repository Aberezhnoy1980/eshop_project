package ru.aberezhnoy.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.aberezhnoy.persist.model.Category;
import ru.aberezhnoy.persist.model.User;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {
    @Query("select distinct c " +
            "from Category c " +
            "left join fetch c.products " +
            "where c.name = :name")
    Optional<User> findByCategoryName(@Param("name") String name);
}
