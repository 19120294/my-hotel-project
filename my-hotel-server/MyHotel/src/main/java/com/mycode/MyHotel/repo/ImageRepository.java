package com.mycode.MyHotel.repo;

import com.mycode.MyHotel.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
//    List<Image> findByProductId(Long id);
}
