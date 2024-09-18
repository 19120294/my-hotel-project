package com.mycode.MyHotel.service.interfac;

import com.mycode.MyHotel.dto.ImageDTO;
import com.mycode.MyHotel.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image getImageById(Long id);
    ImageDTO saveImage(MultipartFile file);
    void deleteImageById(Long id);
//    List<ImageDTO> saveImages(Long productId, List<MultipartFile> files);
    void updateImage(MultipartFile file,  Long imageId);
}
