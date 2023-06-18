package ru.aberezhnoy.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aberezhnoy.persist.model.Picture;

public interface PictureRepository extends JpaRepository<Picture, Long> {
}
