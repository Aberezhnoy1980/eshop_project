package ru.aberezhnoy.service;

import java.util.Optional;

public interface PictureService {

    Optional<String> getPictureContentType(Long id);

    Optional<byte[]> getPictureDataById(Long id);

    String createPicture(byte[] pictureData);
}
