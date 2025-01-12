package com.rajasekhar.dreamshops.service.image;

import com.rajasekhar.dreamshops.dto.ImageDto;
import com.rajasekhar.dreamshops.model.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageservice {
    Image getImageById(Long id);
    void deleteImage(Long id);
    List<ImageDto> saveImage(List<MultipartFile> files, Long productId);
    void updateImage(MultipartFile file, Long imageId);



}
