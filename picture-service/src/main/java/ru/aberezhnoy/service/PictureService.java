package ru.aberezhnoy.service;

import ru.aberezhnoy.model.PictureDto;

import java.util.Optional;

public interface PictureService {

    Optional<PictureDto> getPictureDataById(long id);

    String createPicture(byte[] pictureData);
}
