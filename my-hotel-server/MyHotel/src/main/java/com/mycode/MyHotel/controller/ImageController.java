package com.mycode.MyHotel.controller;

import com.mycode.MyHotel.dto.APIResponse;
import com.mycode.MyHotel.dto.ImageDTO;
import com.mycode.MyHotel.dto.Response;
import com.mycode.MyHotel.entity.Booking;
import com.mycode.MyHotel.entity.Image;
import com.mycode.MyHotel.exception.OurException;
import com.mycode.MyHotel.service.interfac.IImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("/images")

public class ImageController {
    private final IImageService imageService;


    @PostMapping("/upload")
    public ResponseEntity<APIResponse> saveImages(@RequestParam MultipartFile file) {
        try {
            ImageDTO imageDTO = imageService.saveImage(file);
            return ResponseEntity.ok(new APIResponse("Upload success!", imageDTO));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse("Upload failed!", e.getMessage()));
        }

    }

    @GetMapping("/image/download/{imageId}")
    public ResponseEntity<Resource> downloadImage(@PathVariable Long imageId) throws SQLException {
        Image image = imageService.getImageById(imageId);
        ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1, (int) image.getImage().length()));
        return  ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +image.getFileName() + "\"")
                .body(resource);
    }

    @PutMapping("/image/{imageId}/update")
    public ResponseEntity<APIResponse> updateImage(@PathVariable Long imageId, @RequestBody MultipartFile file) {
        try {
            Image image = imageService.getImageById(imageId);
            if(image != null) {
                imageService.updateImage(file, imageId);
                return ResponseEntity.ok(new APIResponse("Update success!", null));
            }
        } catch (OurException e) {
            return  ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse("Update failed!", INTERNAL_SERVER_ERROR));
    }



    @DeleteMapping("/image/{imageId}/delete")
    public ResponseEntity<APIResponse> deleteImage(@PathVariable Long imageId) {
        try {
            Image image = imageService.getImageById(imageId);
            if(image != null) {
                imageService.deleteImageById( imageId);
                return ResponseEntity.ok(new APIResponse("Delete success!", null));
            }
        } catch (OurException e) {
            return  ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse("Delete failed!", INTERNAL_SERVER_ERROR));
    }
}
