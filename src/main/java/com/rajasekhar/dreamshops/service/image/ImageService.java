package com.rajasekhar.dreamshops.service.image;

import com.rajasekhar.dreamshops.dto.ImageDto;
import com.rajasekhar.dreamshops.exceptions.ResourceNotFoundException;
import com.rajasekhar.dreamshops.model.Image;
import com.rajasekhar.dreamshops.model.Product;
import com.rajasekhar.dreamshops.repository.ImageRepository;
import com.rajasekhar.dreamshops.service.product.IProductService;
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
public class ImageService implements IImageservice{

    private final ImageRepository imagerepository;
    private final IProductService productservice;






    @Override
    public Image getImageById(Long id) {
        return imagerepository.findById(id).orElseThrow(()->new ResourceNotFoundException("No image found with id: " + id));

    }

    @Override
    public void deleteImage(Long id) {
        imagerepository.findById(id).ifPresentOrElse(imagerepository::delete,()->{
            throw new ResourceNotFoundException("No image found with id: " + id);
        });
    }

    @Override
    public List<ImageDto> saveImage(List<MultipartFile> files, Long productId) {
        Product product = productservice.getProductById(productId);
        List<ImageDto> savedImageDto = new ArrayList<>();
        for (MultipartFile file : files) {
            try{
                Image image = new Image();
                image.setFilename(file.getOriginalFilename());
                image.setFilename(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                String buildDownloadUrl="/api/v1/images/image/download/";
                String downloadUrl=buildDownloadUrl+image.getId();
                image.setDownloadUrl(downloadUrl);
                imagerepository.save(image);
                Image savedImage=imagerepository.save(image);
                savedImage.setDownloadUrl(buildDownloadUrl+savedImage.getId());
                imagerepository.save(savedImage);


                ImageDto imageDto=new ImageDto();
                imageDto.setImageId((savedImage.getId()));
                imageDto.setImageName(savedImage.getFilename());
                imageDto.setDownloadUrl(savedImage.getDownloadUrl());
                savedImageDto.add(imageDto);



            }catch (IOException | SQLException e){
                throw new RuntimeException(e.getMessage());

            }
        }
        return savedImageDto ;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image image = getImageById(imageId);
        try{
            image.setFilename(file.getOriginalFilename());
            image.setImage(new SerialBlob(file.getBytes()));
            imagerepository.save(image);
        }catch (IOException | SQLException e){
            throw new RuntimeException(e.getMessage());
        }



    }
}
