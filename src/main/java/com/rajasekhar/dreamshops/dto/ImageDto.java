package com.rajasekhar.dreamshops.dto;

import lombok.Data;

@Data
public class ImageDto {
    private Long imageId;
    private String imageName;
    private String imagePath;
    private String downloadUrl;


}
