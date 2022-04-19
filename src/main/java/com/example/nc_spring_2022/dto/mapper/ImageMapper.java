package com.example.nc_spring_2022.dto.mapper;

import com.example.nc_spring_2022.dto.model.ImageDto;
import com.example.nc_spring_2022.model.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ImageMapper {
    public ImageDto createFrom(Image image) {
        ImageDto imageDto = new ImageDto();

        imageDto.setId(image.getId());
        imageDto.setName(image.getName());
        imageDto.setType(image.getType());
        imageDto.setRelatedEntityId(image.getRelatedEntityId());

        return imageDto;
    }
}
