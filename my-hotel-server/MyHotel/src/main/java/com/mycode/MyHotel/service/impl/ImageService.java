package com.mycode.MyHotel.service.impl;

import com.mycode.MyHotel.dto.ImageDTO;
import com.mycode.MyHotel.entity.Image;
import com.mycode.MyHotel.entity.Room;
import com.mycode.MyHotel.exception.OurException;
import com.mycode.MyHotel.repo.ImageRepository;
import com.mycode.MyHotel.service.interfac.IImageService;
import com.mycode.MyHotel.service.interfac.IRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {
    private final ImageRepository imageRepository;
    private final IRoomService roomService;

    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new OurException("No image found with id: " + id));
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepository.findById(id).ifPresentOrElse(imageRepository::delete, () -> {
            throw new OurException("No image found with id: " + id);
        });

    }

    @Override
    public ImageDTO saveImage(MultipartFile file){
        ImageDTO imageDto = new ImageDTO();
        try{
            Image image = new Image();
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImage(new SerialBlob(file.getBytes()));

            String buildDownloadUrl = "/images/image/download/";
            String downloadUrl = buildDownloadUrl+image.getId();
            image.setDownloadUrl(downloadUrl);
            Image savedImage = imageRepository.save(image);

            savedImage.setDownloadUrl(buildDownloadUrl+savedImage.getId());
            imageRepository.save(savedImage);

            imageDto.setId(savedImage.getId());
            imageDto.setFileName(savedImage.getFileName());
            imageDto.setDownloadUrl(savedImage.getDownloadUrl());

        }   catch(IOException | SQLException e){
            throw new RuntimeException(e.getMessage());
        }
        return imageDto;
    }


    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image image = getImageById(imageId);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

    }
}